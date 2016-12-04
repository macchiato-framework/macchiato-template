(ns {{project-ns}}.app
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [{{project-ns}}.core-test]))

(doo-tests '{{project-ns}}.core-test)


