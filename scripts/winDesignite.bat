
set target=%1
set output=%2

java -jar resources/designite.jar -i %target% -o outputs/%output%_DESIGNITE >> outputs/%output%_DESIGNITE/output.txt