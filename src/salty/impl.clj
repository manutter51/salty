(ns salty.impl
  (:import [org.openqa.selenium By WebDriver WebElement]
           org.openqa.selenium.firefox.FirefoxDriver
           [org.openqa.selenium.support.ui ExpectedCondition WebDriverWait]))

;; Convenience test, just to have something quick to type at the repl
(defn test-with-google []
  (let [driver (FirefoxDriver.)]
    (.get driver "http://www.google.com/")
    (let [element (.findElement driver (By/name "q"))]
      (.sendKeys element (into-array ["clojure\n"]))
      (.submit element)
      (println "Page title is " (.getTitle driver)))))