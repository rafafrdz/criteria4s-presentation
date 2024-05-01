#!/bin/bash
psql -c 'create database airbnb;' -U admin
psql -U admin -d airbnb -f /raw/data/initialdb.sql
