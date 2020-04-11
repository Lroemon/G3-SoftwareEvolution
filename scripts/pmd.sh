#! /bin/bash

target=$1
format=${2:-text}
name="$(basename "$target")"
shopt -s expand_aliases
source $HOME/.bash_aliases
pmdalias pmd -d $target -f $format -language java -R rulesets/java/quickstart.xml -reportfile outputs/${name}_PMD.${format}
