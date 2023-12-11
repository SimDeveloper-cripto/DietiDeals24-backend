
    alter table auctions 
       drop 
       foreign key FKpvixwpkykdb4kcebribl4ucwc;

    alter table items 
       drop 
       foreign key FKft8pmhndq1kntvyfaqcybhxvx;

    alter table offers 
       drop 
       foreign key FKjy6rqsgkkdnaoh6752x8osa24;

    alter table offers 
       drop 
       foreign key FK9yilcimbeupq2lyrqr1nlrjyb;

    drop table if exists auctions;

    drop table if exists items;

    drop table if exists offers;

    drop table if exists users;
