package com.ge.onchron.verif.entrypoints;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomRunList {
    public static void main( String[] args ) throws IOException {
        System.out.println( "Adding @from_list tag to tests from provided list." );
        String testsList = System.getProperty( "tests.list" );
        if ( testsList == null ) {
            System.out.println("tests.list environment variable not set.");            
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<String> tcNames = mapper.readValue( testsList, new TypeReference<>() {
        } );
        List<String> notFoundNames = new ArrayList<>( tcNames );

        String storiesPath = Paths.get( System.getProperty( "user.dir" ),
                "src", "test", "resources", "stories" ).toString();
        Collection<File> files = FileUtils.listFiles( new File( storiesPath ), new String[]{"feature"}, true );
        int count = tcNames.size();
        for ( File file : files ) {
            String text = FileUtils.readFileToString( file, StandardCharsets.UTF_8 );
            for ( int i = 0; i < notFoundNames.size(); i++ ) {
                String name = notFoundNames.get( i );
                Pattern pattern = Pattern.compile( "^(Scenario.*" + Pattern.quote( name ) + ")$", Pattern.MULTILINE );
                Matcher matcher = pattern.matcher( text );
                String newText = matcher.replaceAll( "@from_list\n$1" );
                if ( !text.equals( newText ) ) {
                    notFoundNames.remove( name );
                    i--;
                }
                text = newText;
            }
            FileUtils.writeStringToFile( file, text, StandardCharsets.UTF_8 );
        }

        System.out.println( notFoundNames.isEmpty() ? "Tagged all " + count + " tests from list." :
                "Tagged " + (count - notFoundNames.size()) + " tests from list.\n" +
                        "Following " + notFoundNames.size() + " tests were not found:\n" +
                        String.join( "\n", notFoundNames ) );
    }
}
