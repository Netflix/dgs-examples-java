.PHONY: help run check
.DEFAULT_GOAL := help

SHELL = /bin/sh

## Gradle
GW = ./gradlew
GFLAGS ?=
GW_CMD = $(GW) $(GFLAGS)

run: ## Runs the service, with the local profile enabled
	$(GW_CMD) $(GW_OPT) bootRun --args='--spring.profiles.active=local'

check: ## Runs gradlew check
	$(GW_CMD) $(GW_OPT) check

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
