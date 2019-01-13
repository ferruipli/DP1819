start transaction;

use `Acme-HandyWorker`;

revoke all privileges on `Acme-HandyWorker`.* from 'acme-user'@'%';
revoke all privileges on `Acme-HandyWorker`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-HandyWorker`;

commit;