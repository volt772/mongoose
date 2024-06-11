#!/bin/bash

NC='\033[0m'
RED='\033[0;31m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
ONRED='\033[41m'
ONPURPLE='\033[45m'

echo '
     _             _               _                      _ _
    | |           (_)             (_)           _        | | |
  _ | | ____ _   _ _  ____ ____    _ ____   ___| |_  ____| | | ____  ____
 / || |/ _  ) | | | |/ ___) _  )  | |  _ \ /___)  _)/ _  | | |/ _  )/ ___)
( (_| ( (/ / \ V /| ( (__( (/ /   | | | | |___ | |_( ( | | | ( (/ /| |
 \____|\____) \_/ |_|\____)____)  |_|_| |_(___/ \___)_||_|_|_|\____)_|
---------------------------------------------------------------------------
'

#: 설치
makeInstallToDevice() {
	device=$1
	version=$2

	echo "\n-----------------------------------------------------------------------------------------------------------------------------------------------\n"
	echo "▶▶▶▶▶ [INSTALLING : Device : ${BLUE}${device}${NC}, Version : ${PURPLE}${version}${NC}]"
	echo "java -jar $BUNDLETOOL_FILE install-apks --apks=./mongoose-release-"$version"-universal.apks --device-id="$device""
	echo "\n-----------------------------------------------------------------------------------------------------------------------------------------------"

	#: 설치
	java -jar $BUNDLETOOL_FILE install-apks --apks=./mongoose-release-"$version"-universal.apks --device-id="$device"
	exit
}

read -p "설치대상 TAG 입력: " BTAG
BASE_DIR="/Users/allen/work/sources/mongoose"
RELEASE_BUILD_DIR="$BASE_DIR/release_mongoose_$BTAG""_bundle"

#: 빌드 디렉토리 검사
if [ ! -d "$RELEASE_BUILD_DIR" ]; then
    echo "\n----------------------------------------------------------------------------------\n"
    echo "${RED}× 버전 및 디렉토리 없음: 종료\n${NC}"
    echo "----------------------------------------------------------------------------------"
    exit
fi

#: 경로이동
cd $RELEASE_BUILD_DIR

#: 기기리스트
adb devices > devices.txt

names=`cat ./devices.txt | awk '{print $1}' | grep -v 'List'`

SAVEIFS=$IFS   # Save current IFS
IFS=$'\n'      # Change IFS to new line
names=($names) # split to array $names
IFS=$SAVEIFS   # Restore IFS

deviceCount=${#names[*]}
deviceTarget=""

rm ./devices.txt

#: 조건검사
if [[ deviceCount -eq 0 ]]; then
	#: [조건1] : 기기연결여부 > 종료
	echo "-------------------------------------------\n"
	echo "${RED}× 연결된 기기없음\n${NC}"
	echo "-------------------------------------------"
	exit
elif [[ deviceCount -eq 1 ]]; then
	#: [조건2] : 단일기기연결 > 진행
	deviceTarget=${names[0]}
else
	#: [조건3] : 복수기기연결 > 진행
	echo "\n-------------------------------------------\n"
	echo "▼ 현재 연결된 기기선택"

	for (( i=0; i<${#names[@]}; i++ ))
	do
		echo "  ▷ [$i] : ${names[$i]}"
	done

	#: 기기선택
	echo "\n-------------------------------------------\n"
	read -p "  ▷ 기기번호 선택 [0 ~ $(($deviceCount-1))] : " DEVICESNUMBER

	case $DEVICESNUMBER in
		''|*[!0-9]*)
					echo "\n-------------------------------------------\n"
					echo "${RED}× 유효하지 않은 선택지\n${NC}"
					echo "-------------------------------------------"
					exit;;
		*) deviceTarget=${names[$DEVICESNUMBER]} ;;
	esac
fi

#: 최종 값 검증
if [[ -z "$deviceTarget" ]]; then
	echo "\n-------------------------------------------\n"
	echo "${RED}× 설치대상 없음\n${NC}"
	echo "-------------------------------------------"
	exit
elif [[ -z "$BTAG" ]]; then
	echo "\n-------------------------------------------\n"
	echo "${RED}× 설치버전 없음\n${NC}"
	echo "-------------------------------------------"
	exit
elif [ ! -f "$BUNDLETOOL_FILE" ]; then
	echo "\n-------------------------------------------\n"
	echo "${RED}× BundleTool 파일 없음\n${NC}"
	echo "-------------------------------------------"
	exit
fi

#: 최종설치
if [[ deviceCount -eq 1 ]]; then
	#: 기기 1대일경우 검사
	echo "----------------------------------------------------------------------------------\n"
	read -p "▶ 기기명 : '${deviceTarget}', 버전 : '${BTAG}' 앱을 설치하시겠습니까? [Yy / Nn] : " yn
	case $yn in
		[Yy]* ) makeInstallToDevice "$deviceTarget" "$BTAG" break;;
		[Nn]* )
			echo "\n----------------------------------------------------------------------------------\n"
			echo "${RED}× 사용자 취소\n${NC}"
			echo "----------------------------------------------------------------------------------"
			exit;;
		* ) echo
			echo "\n----------------------------------------------------------------------------------\n"
			echo "${RED}× 입력범위 오류, 취소\n${NC}"
			echo "----------------------------------------------------------------------------------"
	esac
else
	#: 그외
	makeInstallToDevice "$deviceTarget" "$BTAG"
fi
