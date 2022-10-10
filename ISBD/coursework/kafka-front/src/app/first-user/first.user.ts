export class FirstUser {
  id;
  name;
  surname;
  nameOfDocument;

  constructor(id: number, name: string, surname: string, nameOfDocument: string){
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.nameOfDocument = nameOfDocument;
  }
};
