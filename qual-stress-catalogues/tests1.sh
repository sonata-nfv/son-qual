#!/bin/bash
printf "\n\n======== POST son-package files to Catalogue ========\n\n\n"
virtualenv ./qual-stress-catalogues/venv
source ./qual-stress-catalogues/venv/bin/activate
pip install -q -r ./qual-stress-catalogues/requirements.txt
python ./qual-stress-catalogues/son-packages_stress.py $1
deactivate
