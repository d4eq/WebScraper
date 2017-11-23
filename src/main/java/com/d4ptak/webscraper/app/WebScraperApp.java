package com.d4ptak.webscraper.app;

class WebScraperApp {
    public static void main(String[] args) {
        WebScraper webScraper = new WebScraper();
        webScraper.cliParse(args);
        webScraper.init();
    }
}
