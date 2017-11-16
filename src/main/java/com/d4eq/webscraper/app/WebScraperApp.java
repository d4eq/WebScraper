package com.d4eq.webscraper.app;

class WebScraperApp {
    public static void main(String[] args) {
        WebScraper webScraper = new WebScraper();
        webScraper.cliParse(args);
        webScraper.init();
    }
}
