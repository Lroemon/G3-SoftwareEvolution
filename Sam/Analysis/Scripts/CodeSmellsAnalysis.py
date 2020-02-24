import pandas as p
import sys
import os

# Parameters
DESIGNITE_PATH = "DesigniteJava.jar"

def program():
    if(len(sys.argv) != 3):
        print("Missing argument ! \ncmd : ./{sys.argv[0]} projectPath output")
        return
    
    projectPath = sys.argv[1]
    output = sys.argv[2]
    analysisCommand = f"java -jar {DESIGNITE_PATH} -i {projectPath} -o {output}/designite/"
    osReturned = os.system(analysisCommand)


if __name__ == "__main__":
    program()
