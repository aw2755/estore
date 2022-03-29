import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl = "https://localhost:8080/user";

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  ngOnInit(): void {
      
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl)
    .pipe(
      tap(_ => this.log('fetched users')),
      catchError(this.handleError<User[]>('getUsers', []))
    );
  }

  /** GET User by id. Return `undefined` when id not found */
  getUserNo404<Data>(name : String): Observable<User> {
    const url = `${this.usersUrl}/?name=${name}`;
    return this.http.get<User[]>(url)
      .pipe(
        map(users => users[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} user name=${name}`);
        }),
        catchError(this.handleError<User>(`getUser name=${name}`))
      );
  }

  /** GET User by name. Will 404 if id not found */
  getUser(name: String): Observable<User> {
    const url = `${this.usersUrl}/${name}`;
    return this.http.get<User>(url).pipe(
      tap(_ => this.log(`fetched user name=${name}`)),
      catchError(this.handleError<User>(`getUser name=${name}`))
    );
  }

    /** POST: add a new User to the server */
  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptions).pipe(
        tap((newUser: User) => this.log(`added user w/ name=${newUser.username}`)),
         catchError(this.handleError<User>('adduser'))
       );
     }

/**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
 private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // TODO: better job of transforming error for user consumption
    this.log(`${operation} failed: ${error.message}`);

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

/** Log a UserService message with the MessageService */
private log(message: string) {
  this.messageService.add(`UserService: ${message}`);
}

}
