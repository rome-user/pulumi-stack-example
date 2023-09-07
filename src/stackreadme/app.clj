(ns stackreadme.app
  (:gen-class)
  (:import (com.pulumi Context Pulumi)
           (com.pulumi.core Output)))

(defmacro ->Consumer [f]
  `(reify java.util.function.Consumer
     (accept [this arg#]
       (~f arg#))))

(defn -main [& args]
  (Pulumi/run
    (->Consumer (fn [^Context ctx]
                  (let [str-var "foo"
                        arr-var ["fizz" "buzz"]
                        readme  (slurp "./Pulumi.README.md")]
                    (doto ctx
                      (.export "str-var" (Output/of str-var))
                      (.export "arr-var" (Output/of arr-var))
                      (.export "readme"  (Output/of readme))))))))
