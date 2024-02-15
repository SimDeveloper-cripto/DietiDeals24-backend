
    create table auctions (
        `active` bit,
        amount_of_time_to_reset integer,
        auction_id integer not null auto_increment,
        auction_type tinyint not null check (auction_type between 0 and 1),
        current_offer_value float(23) not null,
        expiration_date date,
        item_id integer not null,
        owner_id integer not null,
        winner_id integer,
        expiration_time datetime(6),
        primary key (auction_id)
    ) engine=InnoDB;

    create table items (
        base_prize float(23) not null,
        item_id integer not null auto_increment,
        user_id integer not null,
        category varchar(255) not null,
        description TEXT not null,
        name varchar(255) not null,
        image LONGBLOB not null,
        primary key (item_id)
    ) engine=InnoDB;

    create table offers (
        auction_id integer not null,
        offer float(23) not null,
        offer_date date not null,
        offer_time time(6) not null,
        user_id integer not null,
        offer_id bigint not null auto_increment,
        primary key (offer_id)
    ) engine=InnoDB;

    create table users (
        user_id integer not null auto_increment,
        bio TEXT,
        email varchar(255) not null,
        name varchar(255) not null,
        password varchar(255) not null,
        surname varchar(255) not null,
        web_site_url varchar(255),
        primary key (user_id)
    ) engine=InnoDB;

    alter table auctions
       add constraint UK_j127fy4j3xa1ircf7vmx4lxqo unique (item_id);

    alter table users
       add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

    alter table auctions
       add constraint FKpvixwpkykdb4kcebribl4ucwc
       foreign key (item_id)
       references items (item_id);

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
