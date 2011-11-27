(ns salty.wait
  (:import [org.openqa.selenium.support.ui WebDriverWait ExpectedCondition]))

(defn wait
  [driver timeout & conditions]
  (-> (WebDriverWait. driver timeout)
      (.until (proxy [ExpectedCondition] []
                (apply [drvr]
                  (some (map #(% drvr) conditions)))))))