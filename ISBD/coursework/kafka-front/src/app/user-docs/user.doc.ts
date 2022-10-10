export class UserDoc {
  id;
  name;
  date1;
  date2;
  bywho;

  constructor(id: number, name: number, date1: Date, date2: Date, bywho: string){
    this.id = id;
    this.name = name;
    this.bywho = bywho;
    this.date1 = date1;
    this.date2 = date2;
  }
};
