#/bin/bash
javac -cp . -d class $(find ./src -name *.java)

# execute:
# java -cp ./class synth.SynthMain [log_file] [test_file] [timeout_per_synthesis]
