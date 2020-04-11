#! /bin/bash

target=$1
name="$(basename "$target")"
java -jar resources/designite.jar -i $target -o outputs/${name}_DESIGNITE
# -r JAiSLawGQ0wu5LTy
