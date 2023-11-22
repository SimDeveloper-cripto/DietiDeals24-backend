
    create table auctions (
        auction_id integer not null auto_increment,
        auction_type tinyint not null check (auction_type between 0 and 1),
        current_offer_value float(23) not null,
        duration decimal(21,0) not null,
        primary key (auction_id)
    ) engine=InnoDB;

    create table items (
        auction_id integer not null,
        base_prize float(23) not null,
        item_id integer not null auto_increment,
        user_id integer not null,
        category varchar(255) not null,
        description TEXT not null,
        name varchar(255) not null,
        image tinyblob,
        primary key (item_id)
    ) engine=InnoDB;

    create table offers (
        auction_id integer not null,
        offer float(23) not null,
        user_id integer not null,
        offer_date_and_time datetime(6) not null,
        offer_id bigint not null auto_increment,
        primary key (offer_id)
    ) engine=InnoDB;

    create table users (
        user_id integer not null auto_increment,
        email varchar(255) not null,
        name varchar(255) not null,
        password varchar(255) not null,
        surname varchar(255) not null,
        primary key (user_id)
    ) engine=InnoDB;

    alter table users 
       add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

    alter table items 
       add constraint FKf38jo26w2mxvqikam0v18pvm4 
       foreign key (auction_id) 
       references auctions (auction_id);

    alter table items 
       add constraint FKft8pmhndq1kntvyfaqcybhxvx 
       foreign key (user_id) 
       references users (user_id);

    alter table offers 
       add constraint FKjy6rqsgkkdnaoh6752x8osa24 
       foreign key (auction_id) 
       references auctions (auction_id);

    alter table offers 
       add constraint FK9yilcimbeupq2lyrqr1nlrjyb 
       foreign key (user_id) 
       references users (user_id);
