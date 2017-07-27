#!/bin/bash

# CI entry point
# automatic packing of service projects to validate them
# (uses son-cli tools installed directly from git)

# be sure that old runs do not break things
rm -rf test_ws son-cli

set -e

git clone https://github.com/sonata-nfv/son-cli.git
cd son-cli
virtualenv -p /usr/bin/python3 venv
source venv/bin/activate
pip3 install flask
pip3 install numpy
pip3 install scipy
pip3 install docker==2.0.2
pip3 install matplotlib
python bootstrap.py
bin/buildout
python setup.py develop
cd ..

# create a test workspace
son-workspace --init --workspace test_ws

# package all example service projects
which son-package

# service platform projects
son-package --workspace test_ws --project qual-1VNF-1PoP -n qual-1VNF-1PoP
son-package --workspace test_ws --project qual-2VNF-1PoP -n qual-2VNF-1PoP

# leave venv
deactivate

# remove test workspace
rm -rf test_ws

# remove son-cli
rm -rf son-cli

# Note: The packaged services are not yet uploaded anywhere. We use packaging only to validate the service projects and their descriptors.
