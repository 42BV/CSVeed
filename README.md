CSVeed
======

[![Java CI](https://github.com/42BV/CSVeed/workflows/Java%20CI/badge.svg)](https://github.com/42BV/CSVeed/actions?query=workflow%3A%22Java+CI%22)
[![Coverage Status](https://coveralls.io/repos/github/42BV/CSVeed/badge.svg?branch=master)](https://coveralls.io/github/42BV/CSVeed?branch=master)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.csveed/csveed/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.csveed/csveed)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Be sure to check the project site at [csveed.org](http://csveed.org).

CSVeed is a Java library for reading [Comma Separated Value (CSV)](http://tools.ietf.org/html/rfc4180)
files and exposing those either as Rows or Java Beans. In order to use CSVeed in your project,
simply add the following dependency:

```xml
        <dependency>
            <groupId>org.csveed</groupId>
            <artifactId>csveed</artifactId>
            <version>0.7.4</version>
        </dependency>
```

For optimal usage, make sure you have a [SLF4J](http://www.slf4j.org/manual.html) configured. If you have no
previous SLF4J logger configured and you want to get up and running quickly, use this:

```xml
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.6</version>
        </dependency>
```

Getting Started Real Quick
--------------------------
No time to waste, so here we go. Let us assume you have a Java Bean you want to convert your CSV rows into:

```java
        import org.csveed.annotations.CsvDate;

        import java.util.Date;

        public class Bean {
            private String name;
            private Long number;
            @CsvDate(format="dd-MM-yyyy")
            private Date date;

            public String getName() { return name; }
            public Long getNumber() { return number; }
            public Date getDate() { return date; }
            public void setName(String name) { this.name = name; }
            public void setNumber(Long number) { this.number = number; }
            public void setDate(Date date) { this.date = date; }
        }
```

Note that the Date property has been annotated with @CsvDate, one of the
[CSVeed annotations](https://github.com/42BV/CSVeed/wiki/Annotations). This allows CSVeed to convert the
CSV text value into a java.util.Date using the date format in the annotation.

```java
        Reader reader = new StringReader(
                "name;number;date\n"+
                "\"Alpha\";1900;\"13-07-1922\"\n"+
                "\"Beta\";1901;\"22-01-1943\"\n"+
                "\"Gamma\";1902;\"30-09-1978\""
        );

        CsvClient<Bean> csvClient = new CsvClientImpl<Bean>(reader, Bean.class);
        final List<Bean> beans = csvClient.readBeans();

        for (Bean bean : beans) {
            System.out.println(bean.getName()+" | "+bean.getNumber()+" | "+bean.getDate());
        }
```

It's that simple to get up and running. You could also opt to declare your instructions programmatically:

```java
        Reader reader = new StringReader(
                "name;number;date\n"+
                "\"Alpha\";1900;\"13-07-1922\"\n"+
                "\"Beta\";1901;\"22-01-1943\"\n"+
                "\"Gamma\";1902;\"30-09-1978\""
        );

        CsvClient<Bean> csvClient = new CsvClientImpl<Bean>(reader, new BeanInstructionsImpl(Bean.class))
                .setMapper(ColumnNameMapper.class)
                .mapColumnNameToProperty("name", "name")
                .mapColumnNameToProperty("number", "number")
                .mapColumnNameToProperty("date", "date")
                .setDate("date", "dd-MM-yyyy");
        final List<Bean> beans = csvClient.readBeans();

        for (Bean bean : beans) {
            System.out.println(bean.getName()+" | "+bean.getNumber()+" | "+bean.getDate());
        }
```

Be sure to check out the [WIKI](https://github.com/42BV/CSVeed/wiki) for more information on CSVeed.

License
-------
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
