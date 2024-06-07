#!/bin/bash

NC='\033[0m'
RED='\033[0;31m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
ONRED='\033[41m'
ONPURPLE='\033[45m'

echo '
 ______        _ _     _
(____  \      (_) |   | |
 ____)  )_   _ _| | _ | | ____  ____
|  __  (| | | | | |/ || |/ _  )/ ___)
| |__)  ) |_| | | ( (_| ( (/ /| |
|______/ \____|_|_|\____|\____)_|
-----------------------------------------
'
read -p "Enter Mongoose Git TAG: " BUILD_TAG
BASE_DIR="/Users/allen/work/sources/mongoose"
BUILD_DIR="$BASE_DIR/release_mongoose_$BUILD_TAG""_bundle"

#: Check Old Directory is exists?
if [ -d "$BUILD_DIR" ]; then
	echo "----------------------------------------------------------------------------------\n"
	read -p "Directory Exists, Delete and Retry? [Yy / Nn] : " yn
	case $yn in
		[Yy]* ) rm -rf $BUILD_DIR break;;
		[Nn]* )
			echo "\n----------------------------------------------------------------------------------\n"
			echo "${RED}× User CANCEL\n${NC}"
			echo "----------------------------------------------------------------------------------"
			exit;;
		* ) echo
			echo "\n----------------------------------------------------------------------------------\n"
			echo "${RED}× Range Error, CANCELLED\n${NC}"
			echo "----------------------------------------------------------------------------------"
	esac
fi

#: EXPORT TAG NAME
export BUILD_TAG

#: BUILD
cd $BASE_DIR
sh ./buildReleaseAppBundleLocal.sh

BUILD_DIR="$BASE_DIR/release_mongoose_$BUILD_TAG""_bundle"

cd $BUILD_DIR
CURR_DIR=`pwd`

if [[ "$CURR_DIR" != $BUILD_DIR ]]; then
    echo "${RED}× Wrong PATH\n\n"
    exit
fi

#jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 -keystore ../mongoose_keystore/mongoose_keystore.jks ./mongoose-release-$BUILD_TAG.aab mongoose_alias
