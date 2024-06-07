#!/bin/bash

## check key store
KEY_STORE_DIR="./mongoose_keystore";

if [ ! -d "$KEY_STORE_DIR" ]; then
	echo "USAGE: run it on keystore directory depth level"
	echo "$ ./mongoose/buildReleaseLocal.sh"
	exit
fi

## check android app bundle tool
if [ ! -f "$BUNDLETOOL_FILE" ]; then
	echo "Missing android bundle tool file"
	exit
fi

## start
read -p "Enter Mongoose Git TAG: " BTAG

BUILD_DIR="./tmp-mongoose-bundle-$BTAG"

mkdir -p $BUILD_DIR
cp -rf ./* $BUILD_DIR
cd $BUILD_DIR

## build aeolos
read -p "Enter KeyStore Password : " KSPW
read -p "Enter Key Password : " KPW

export "MONGOOSE_KEYSTORE_PW="$KSPW
export "MONGOOSE_KEY_PW="$KPW

./gradlew clean :app:bundleRelease

## copy apk, mapping file
OUTPUT_DIR="../release_mongoose_$BTAG""_bundle"

mkdir -p $OUTPUT_DIR
cp -rf ./app/build/outputs/bundle/release/* $OUTPUT_DIR
cp -rf ./app/build/outputs/mapping/release/* $OUTPUT_DIR

## make universal APKs
java -jar "$BUNDLETOOL_FILE" build-apks --bundle=./app/build/outputs/bundle/release/app-release.aab --output=$OUTPUT_DIR/mongoose-release-"$BTAG"-universal.apks --ks=../mongoose_keystore/mongoose_keystore.jks --ks-pass=pass:$KSPW --ks-key-alias=mongoose_alias --key-pass=pass:$KPW --mode=universal

## move to output directory
mv $OUTPUT_DIR/app-release.aab $OUTPUT_DIR/mongoose-release-"$BTAG".aab

## delete temporary build directory
cd ..
rm -rf $BUILD_DIR

sync

## end
echo "-------------------------------------------"
echo "App Bundle/APKs Result => $OUTPUT_DIR"
echo "-------------------------------------------"
