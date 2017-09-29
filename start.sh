#!/bin/bash

export DISPLAY=:0.0
screen java -jar MagicMirror.jar -s settingsMirror.json
