import argparse
import base64
import json
from os import path
from getpass import getpass
import requests
from requests.auth import HTTPBasicAuth

orcanos_url = "https://orcanos.apps.ge-healthcare.net/ci/API/V2/JSON/"



def get_filter_results(filter_id, project_ref, item_type, user, password):    
    url = path.join(orcanos_url, "QW_Get_Filter_Results")
    payload = json.dumps({
        "Filter_id": filter_id,
        "Page_no": "1",
        "Page_Size": "9999",
        "Item_Type": item_type,
        "Version_id": project_ref,
        "IsNewPaging": "1",
        "IsReturnPageGount": "0"
    })
    headers = {
        'Content-Type': 'application/json'
    }

    response = requests.post(url, headers=headers, data=payload, auth=HTTPBasicAuth(user, password), verify=False)
    if response.status_code != 200 or "ErrorStatus" in response.text:
        raise RuntimeError(f"Failed to get filter result!\nStatus: {response.status_code}\nBody:{response.text}")

    return response.json()


def main(filter_id, user, password):
    if password is None:
        password = getpass()
    if password.startswith("b64"):
        password = base64.b64decode(password[3:]).decode()
    res = get_filter_results(filter_id, 66, "T_CASE", user, password)
    names = []
    for tc in res['Data']['Object']:
        names.append(next(f for f in tc['Field'] if f['Name'] == 'Obj_name')['Text'])
    payload = {
        "metafilter": "some meta",
        "tests_list": names
    }
    print(json.dumps(names, indent=2))
    # print(base64.b64encode(json.dumps(payload).encode()).decode())


if __name__ == "__main__":
    parser = argparse.ArgumentParser(prog="Get list of test cases from filter")
    parser.add_argument('-f', '--filter', required=True)
    parser.add_argument('-u', '--user', required=True)
    parser.add_argument('-p', '--password')
    args = parser.parse_args()
    main(args.filter, args.user, args.password)
