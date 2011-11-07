(ns salty.impl
  (:import [org.openqa.selenium By WebDriver WebElement]
           org.openqa.selenium.firefox.FirefoxDriver
           [org.openqa.selenium.support.ui ExpectedCondition WebDriverWait]))

;; Convenience test, just to have something quick to type at the repl
(defn test-with-google []
  (let [driver (FirefoxDriver.)]
    (.get driver "http://www.google.com/")
    (println "Original page title is " (.getTitle driver))
    (let [element (.findElement driver (By/name "q"))]
      (.sendKeys element (into-array ["clojure\n"]))
      (.submit element)
      (-> (WebDriverWait. driver 10)
          (.until (proxy [ExpectedCondition] []
                    (apply [d]
                      (-> (.getTitle d)
                          (.toLowerCase)
                          (.startsWith "clojure"))))))
      (println "Page title after searching is " (.getTitle driver))
      (.quit driver))))