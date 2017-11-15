# WebScraper
This is an example Maven project written in Java 8.
## Technology stack
* Jsoup 1.11.1
* HttpClient 4.5.3
* Commons CLI 1.4
* Log4j 4.5.3
* JUnit 4.12
* Mockito 2.9.0
* Maven 3.3.9
## Requirements
* JRE 8
## Features
* Scrap multiple pages
* Save the website scraped data as xml file
## Install / Usage
```
        git clone https://github.com/d4eq/WebScraper.git
        cd WebScraper
        mvn clean install
        cd target
        java -jar WebScraper-1.0.jar -u https://www.ceneo.pl/Filmy_Blu-ray/Gatunek:Sensacyjne;m80;n100.htm
```
## Options
```
    usage: webscraper
        -d,--debug          debug mode
        -h,--help           print this message
        -p,--profile <arg>  input profile name: ceneo-list (default), ceneo-box
        -t,--type <arg>     output type: xml (default)
        -u,--url <arg>      source url (required)
```
 