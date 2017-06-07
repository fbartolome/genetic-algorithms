# Genetic Algorithms

Third Artificial Intelligence System Project.

## Getting Started

These instructions will install the system in your local machine.

### Prerequisites

1. Install Maven, if you haven't yet
    #### Mac OS X
    ```
    brew install maven
    ```

    #### Ubuntu
    ```
    sudo apt-get install maven
    ```

    #### Other OSes
    Check https://maven.apache.org/install.html.

2. Clone the repository or download source code:
```
git clone https://github.com/fbartolome/genetic-algorithms.git
```

### Installing

1. Change working directory to project root (i.e where pom.xml is located):
    ```
    cd <project-root>
    ```

2. Let maven resolve dependencies:
    ```
    mvn dependency:resolve
    ```

3. Create jar file
    ```
    mvn clean package [-D dir=<destination-directory>]
    ```
    Note: If no destination was indicated, jar file will be under ``` <project-root>/target ```


## Usage
The application can be executed running ```java -jar <path-to-jar>```.
It can be configured to use the desired configuration file. Optionally, it can be configured to save a Matlab/Octave file with output data to be processed by Matlab/Octave.

### Options
In order to execute using different options, parameters must be passed to the program. The following sections will describe these parameters

#### Displaying usage message
You can display the usage message by setting the ```-h``` or the ```--help``` parameters.
Example of usage:
```
java -jar <path-to-jar> -h
```

#### Indicating configuration file
To indicate the path to the onfiguration file, you must include the ```-c``` or the ```--config``` parameters.
If this option is not included, the system will try to open a configuration file with the name ```config.json``` located in the current working directory
Example of usage:
```
java -jar <path-to-jar> --config ~/config.json
```

#### Matlab/Octave output switch
To instruct the app to output data for Matlab/Octave, include the ```-m```, ```--matlab```, ```-o``` or the ```--octave``` parameters.
Example of usage:
```
java -jar <path-to-jar> --config ~/config.json --matlab
```

#### Matlab/Octave output file path
To set the path where the system should save the Matlab/Octave file, include the ```-mF```, ```--matlab-file```, ```-oF``` or the ```--octave-file``` parameters.
If the ```-m```, ```--matlab```, ```-o``` or the ```--octave``` parameters, and any of ```-mF```, ```--matlab-file```, ```-oF``` or ```--octave-file``` are included, data will be saved in a file with the name ```output.m``` in the current working directory. 
If any of ```-mF```, ```--matlab-file```, ```-oF``` or ```--octave-file``` is included, but any of ```-m```, ```--matlab```, ```-o``` or ```--octave``` are set, the application will consider that they were included.
Example of usage:
```
java -jar <path-to-jar> --config ~/config.json --octave-file ~/octave-output.m
```

### Parametrization
The app can be parameterized using a configuration file. 
This file must be a JSON format file, complying with the following JSON Schema

#### Json Schema for a configuration file
The following is a JSON Schema, according to http://json-schema.org/
```javascript
     
     {
        "$schema": "http://json-schema.org/draft-04/schema#",
        "description": "Schema for a config file",
        "type": "object",
        "properties": {
            "character": {
                "type": "object",
                "properties": {
                    "type": {
                        "type": "string",
                        "required": true
                    },
                    "strengthMultiplier": {
                        "type": "number",
                        "required": true
                    },
                    "agilityMultiplier": {
                        "type": "number",
                        "required": true
                    },
                    "proficiencyMultiplier": {
                        "type": "number",
                        "required": true
                    },
                    "resistanceMultiplier": {
                        "type": "number",
                        "required": true
                    },
                    "lifeMultiplier": {
                        "type": "number",
                        "required": true
                    }
                },
                "required": true
            },
            "items": {
                "type":"object",
                "properties": {
                    "armorsFilePath": {
                        "type": "string",
                        "required": true
                    },
                    "bootsFilePath": {
                        "type": "string",
                        "required": true
                    },
                    "gauntletsFilePath": {
                        "type": "string",
                        "required": true
                    },
                    "helmetsFilePath": {
                        "type": "string",
                        "required": true
                    },
                    "weaponsFilePath": {
                        "type": "string",
                        "required": true
                    }
                }
            },
            "strategies": {
                "selection": {
                    "type":"object",
                    "oneOf" : [
                        {
                            "$ref": "#/selections/boltzmann"
                        },
                        {
                            "$ref": "#/selections/elite"
                        },
                        {
                            "$ref": "#/selections/hybrid"
                        },
                        {
                            "$ref": "#/selections/ranking"
                        },
                        {
                            "$ref": "#/selections/roulette"
                        },
                        {
                            "$ref": "#/selections/tournament_det"
                        },
                        {
                            "$ref": "#/selections/tournament_prob"
                        },
                        {
                            "$ref": "#/selections/universal"
                        }
                    ],
                    "required":true
                },
                "crossover": {
                    "type": "object",
                    "oneOf" : [
                        {
                            "$ref": "#/crossovers/one_point"
                        },
                        {
                            "$ref": "#/crossovers/two_point"
                        },
                        {
                            "$ref": "#/crossovers/uniform"
                        },
                        {
                            "$ref": "#/crossovers/annular"
                        }
                    ],
                    "required":true
                },
                "mutation":{
                    "type": "object",
                    "oneOf" : [
                        {
                            "$ref": "#/mutations/uniform"
                        }
                    ],
                    "required":true
                },
                "replacement":{
                    "type": "object",
                    "oneOf" : [
                        {
                            "$ref": "#/replacements/first"
                        },
                        {
                            "$ref": "#/replacements/second"
                        },
                        {
                            "$ref": "#/replacements/third"
                        }
                    ],
                    "required":true
                },
                "ending":{
                    "type": "object",
                    "oneOf" : [
                        {
                            "$ref": "#/endings/content"
                        },
                        {
                            "$ref": "#/endings/max"
                        },
                        {
                            "$ref": "#/endings/optimum"
                        },
                        {
                            "$ref": "#/endings/structure"
                        }
                    ],
                    "required":true
                }
            },
            "initialPopulation": {
                "type": "object",
                "properties": {
                    "size": {
                        "type": "number",
                        "required": true
                    },
                    "initialization" : {
                        "type": "string",
                        "enum": ["SORTED", "SHUFFLE", "RANDOM", "RANDOM_SEED"],
                        "required": true
                    },
                    "seed": {
                        "type": "number", // Must be a valid java long value
                        "required": false
                    }
                },
                "required": true
            }
        },



        // Selection options
        "selections" : {
            "boltzmann": {
                "strategy": {
                    "type":"string",
                    "enum": ["BOLTZMANN"]
                },
                "k": {
                    "type": "number",
                    "required":true
                },
                "n": {
                    "type": "number", // Must be a valid java double value
                    "required":true
                }
            },
            "elite": {
                "strategy": {
                    "type":"string",
                    "enum": ["ELITE"]
                },
                "k": {
                    "type": "number",
                    "required":true
                }
            },
            "hybrid": {
                "strategy": {
                    "type":"string",
                    "enum": ["HYBRID"]
                },
                "k": {
                    "type": "number",
                    "required":true
                },
                "methods":{
                    "type": "array",
                    "items": {
                        "type":"object",
                        "properties": {
                            "method": {
                                "type": "object",
                                "oneOf" : [
                                    {
                                        "$ref": "#/selections/boltzmann"
                                    },
                                    {
                                        "$ref": "#/selections/elite"
                                    },
                                    {
                                        "$ref": "#/selections/ranking"
                                    },
                                    {
                                        "$ref": "#/selections/roulette"
                                    },
                                    {
                                        "$ref": "#/selections/tournament_det"
                                    },
                                    {
                                        "$ref": "#/selections/tournament_prob"
                                    },
                                    {
                                        "$ref": "#/selections/universal"
                                    }
                                ],
                                "required":true
                            },
                            "percentage" : {
                                "type": "number", // Must be a valid java double value
                                "required":true
                            }
                        }
                    },
                    "required":true
                    // Note that the sum of all percentages MUST add 1.
                }
            },
            "ranking": {
                "strategy": {
                    "type":"string",
                    "enum": ["RANKING"]
                },
                "k": {
                    "type": "number",
                    "required":true
                }
            },
            "roulette": {
                "strategy": {
                    "type":"string",
                    "enum": ["ROULETTE"]
                },
                "k": {
                    "type": "number",
                    "required":true
                }
            },
            "tournament_det": {
                "strategy": {
                    "type":"string",
                    "enum": ["TOURNAMENT_DETERMINISTIC"]
                },
                "k": {
                    "type": "number",
                    "required":true
                },
                "m": {
                    "type": "number", // Must be a valid java int value
                    "required":true
                }
            },
            "tournament_prob": {
                "strategy": {
                    "type":"string",
                    "enum": ["TOURNAMENT_PROBABILISTIC"]
                },
                "k": {
                    "type": "number",
                    "required":true
                }
            },
            "universal": {
                "strategy": {
                    "type":"string",
                    "enum": ["UNIVERSAL"]
                },
                "k": {
                    "type": "number",
                    "required":true
                }
            }
        },

        // Crossover options
        "crossovers" : {
            "one_point" : {
                "strategy": {
                    "type":"string",
                    "enum": ["ONE_POINT"]
                }
            },
            "two_point" : {
                "strategy": {
                    "type":"string",
                    "enum": ["TWO_POINT"]
                }
            },
            "uniform" : {
                "strategy": {
                    "type":"string",
                    "enum": ["UNIFORM"]
                },
                "p": {
                    "type": "number", // Must be a valid java int value
                    "required":true
                }
            },
            "annular" : {
                "strategy": {
                    "type":"string",
                    "enum": ["ANNULAR"]
                }
            }
        },

        // Mutations options
        "mutations" : {
            "uniform" : {
                "strategy": {
                    "type":"string",
                    "enum": ["UNIFORM"]
                },
                "pm" : {
                    "type": "number", // Must be a valid java double value between 0 and 1
                    "required":true
                }
            }
        },

        // Replacement options
        "replacements": {
            "first": {
                "strategy": {
                    "type":"string",
                    "enum": ["REPLACEMENT_1"]
                }
            },
            "second": {
                "strategy": {
                    "type":"string",
                    "enum": ["REPLACEMENT_2"]
                },
                "oldPopulationSelection": {
                    "type":"object",
                    "oneOf" : [
                        {
                            "$ref": "#/selections/boltzmann"
                        },
                        {
                            "$ref": "#/selections/elite"
                        },
                        {
                            "$ref": "#/selections/hybrid"
                        },
                        {
                            "$ref": "#/selections/ranking"
                        },
                        {
                            "$ref": "#/selections/roulette"
                        },
                        {
                            "$ref": "#/selections/tournament_det"
                        },
                        {
                            "$ref": "#/selections/tournament_prob"
                        },
                        {
                            "$ref": "#/selections/universal"
                        }
                    ],
                    "required":true
                }
            },
            "third": {
                "strategy": {
                    "type":"string",
                    "enum": ["REPLACEMENT_3"]
                },
                "oldPopulationSelection": {
                    "type":"object",
                    "oneOf" : [
                        {
                            "$ref": "#/selections/boltzmann"
                        },
                        {
                            "$ref": "#/selections/elite"
                        },
                        {
                            "$ref": "#/selections/hybrid"
                        },
                        {
                            "$ref": "#/selections/ranking"
                        },
                        {
                            "$ref": "#/selections/roulette"
                        },
                        {
                            "$ref": "#/selections/tournament_det"
                        },
                        {
                            "$ref": "#/selections/tournament_prob"
                        },
                        {
                            "$ref": "#/selections/universal"
                        }
                    ],
                    "required":true
                },
                "newPopulationSelection": {
                    "type":"object",
                    "oneOf" : [
                        {
                            "$ref": "#/selections/boltzmann"
                        },
                        {
                            "$ref": "#/selections/elite"
                        },
                        {
                            "$ref": "#/selections/hybrid"
                        },
                        {
                            "$ref": "#/selections/ranking"
                        },
                        {
                            "$ref": "#/selections/roulette"
                        },
                        {
                            "$ref": "#/selections/tournament_det"
                        },
                        {
                            "$ref": "#/selections/tournament_prob"
                        },
                        {
                            "$ref": "#/selections/universal"
                        }
                    ],
                    "required":true
                }
            }
        },

        // Ending options
        "endings": {
            "content": {
                "strategy": {
                    "type":"string",
                    "enum": ["CONTENT"]
                },
                "generations" : {
                    "type": "number", // Must be a valid java int value
                    "required":true
                }
            },
            "max": {
                "strategy": {
                    "type":"string",
                    "enum": ["MAX_GENERATIONS"]
                },
                "maxGenerations" : {
                    "type": "number", // Must be a valid java int value
                    "required":true
                }
            },
            "optimum": {
                "strategy": {
                    "type":"string",
                    "enum": ["OPTIMUM"]
                },
                "optimumFitness" : {
                    "type": "number", // Must be a valid java double value
                    "required":true
                }
            },
            "structure": {
                "strategy": {
                    "type":"string",
                    "enum": ["STRUCTURE"]
                },
                "tolerance" : {
                    "type": "number", // Must be a valid java double value
                    "required":true
                }
            }
        }
    }
```
#### Example of a configuration file
The following is an example of a configuration file.
```json
{
    "character": {
        "type":"ARCHER",
        "strengthMultiplier":0.8,
        "agilityMultiplier":0.8,
        "proficiencyMultiplier":0.8,
        "resistanceMultiplier":1.1,
        "lifeMultiplier":1.2
    },
    "items": {
        "armorsFilePath":"~/items/armors.tsv",
        "bootsFilePath":"~/items/boots.tsv",
        "gauntletsFilePath":"~/items/gauntlets.tsv",
        "helmetsFilePath":"~/items/helmets.tsv",
        "weaponsFilePath":"~/items/weapons.tsv"  
    },
    "strategies": {
        "selection": {
            "strategy": "BOLTZMANN",
            "n": 0.43
        },
        "crossover" : {
            "strategy":"UNIFORM",
            "p":0.56
        },
        "mutation" : {
            "strategy": "UNIFORM",
            "pm":0.75
        },
        "replacement": {
            "strategy":"REPLACEMENT_3",
            "oldPopulationSelection": 
            {
                "strategy":"RANKING"
            },
            "newPopulationSelection": 
            {
                "strategy":"HYBRID",
                "methods": [
                    {
                        "method": {
                            "strategy":"ELITE"
                        },
                        "percentage": 0.10
                    },
                    {
                        "method": {
                            "strategy": "BOLTZMANN",
                            "n": 0.43
                        },
                        "percentage":0.65
                    },
                    {
                        "method": {
                            "strategy": "ROULETTE"
                        },
                        "percentage":0.25
                    }
        
                ]
            }
        },
        "ending" : {
            "strategy":"MAX_GENERATIONS",
            "maxGenerations":15000
        }
    },
    "initialPopulation":{
        "initialization":"RANDOM_SEED",
        "size":2000,
        "seed":6525145634
    }
}

```

## Authors
* Juan Marcos Bellini
* Natalia Navas
* Francisco Bartolomé