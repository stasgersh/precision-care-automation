import argparse
import glob
import re
from os import path
from pathlib import Path


def main(root_dir=None):
    if root_dir is None:
        root_dir = str(Path(path.abspath(__file__)).parent.parent.parent)
    print(root_dir)
    replacement = "//todo infra method call was here, do not commit!"
    for filename in glob.iglob(root_dir + '/**/whatsteps/**/*.java', recursive=True):
        with open(filename, "r+") as f:
            content = f.read()
            # calls to steps
            content = re.sub(r"^\s+\w+(?<![tT]estDefinition)[sS]teps\.[\s\S]+?\);", replacement, content, flags=re.MULTILINE)
            # variables calling steps
            content = re.sub(r"\w+ ([\w\d]+\s?=)\s?\w+(?<![tT]estDefinition)Steps\.[\s\S]+?\);", fr'Object \g<1> null;{replacement}', content, flags=re.MULTILINE)
            # log evidence using variables in actual value and assertion result
            content = re.sub(r"(testDefinitionSteps.logEvidence[^,]+?,)([^,]+?,)[^,]+?([,]?[\)\s]+?;)", fr'\g<1>\g<2> true \g<3>', content, flags=re.MULTILINE)
            if filename.endswith("BeforeAndAfter.java"):
                content = content.replace("postUIActions();", replacement)
                content = content.replace("userSettingsSteps.userHasAccessToOncoCare( testUser )", "false")

            if filename.endswith("Login.java"):
                content = re.sub(r"if \( patientScreenSteps[\s\S]+?testDefinitionSteps", replacement + "\ntestDefinitionSteps", content)
                content = content.replace("logout();", replacement)

#             if filename.endswith("BadgeConverter.java"):
#                 content = content.replace("map.containsKey( BADGE_COLOR_COLUMN )", "false")

            f.seek(0)
            f.write(content)
            f.truncate()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(prog="Comment all infra calls leaving only test definition calls (addStep and logEvidence")
    parser.add_argument('-r', '--root-dir', default=None)
    args = parser.parse_args()
    main(args.root_dir)
