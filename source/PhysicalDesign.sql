# Authors: Andrew Koenen & Zach DeMaris

use group5;

# ---------------Optimization for Q18 and Q3 Increase buffer pool size from 8.3 Mb to 83.89 Mb
SET GLOBAL innodb_buffer_pool_size = 83886080;
# ---------------Optimization for Q18 and Q3 Increase buffer pool size from 8.3 Mb to 83.89 Mb



# ---------------------- Optimization for Q9
CREATE INDEX sc_idx ON users (sub_category);
# ---------------------- Optimization for Q9



# --------------------- Optimization for Q16 and Q23
ALTER TABLE tweets ADD COLUMN (month INT);
ALTER TABLE tweets ADD COLUMN (year INT);

SET SQL_SAFE_UPDATES = 0;

UPDATE tweets SET month = month(createdTime);
UPDATE tweets SET year = year(createdTime);

CREATE INDEX month_idx ON tweets (month);
CREATE INDEX year_idx ON tweets (year);

DROP INDEX month_idx ON tweets;
DROP INDEX year_idx ON tweets;

SET SQL_SAFE_UPDATES = 1;
# --------------------- Optimization for Q16 and Q23


