import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../core/authentication/authentication.service';


@Component({ templateUrl: 'login.component.html',
             styleUrls: ['login.component.scss'] })
export class LoginComponent {
    loginForm: FormGroup = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.pattern('[A-Za-z0-9\d$@$!%*?&].{8,}')]]
    });;
    registerForm: FormGroup = this.formBuilder.group({
        name: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.pattern('[A-Za-z0-9\d$@$!%*?&].{8,}')]]
    });
    loading = false;
    submitted = false;
    returnUrl!: string;
    loginMessage = '';
    loginAlertType = '';
    registerMessage = '';
    registerAlertType = '';

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService
    ) { 
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) { 
            this.router.navigate(['/home']);
        }
    }

    // convenience getter for easy access to form fields
    get lf() { return this.loginForm.controls; }
    get rf() { return this.registerForm.controls; }

    onLoginSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            this.loginMessage = 'Form Invalid';
            this.loginAlertType = 'danger';
            return;
        }

        this.loading = true;
        this.authenticationService.login(this.lf.email.value, this.lf.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.router.navigate(['/home']);
                },
                error => {
                    this.loginMessage = error;
                    this.loginAlertType = 'danger';
                    this.loading = false;
                }
            );
    }

    onRegisterSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            this.registerMessage = 'Form Invalid';
            this.registerAlertType = 'danger';
            return;
        }

        this.loading = true;
        this.authenticationService.register(this.rf.name.value, this.rf.email.value, this.rf.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    console.log(data);
                    this.registerMessage = "Successfully Created User";
                    this.registerAlertType = 'success';
                },
                error => {
                    this.registerMessage = error;
                    this.registerAlertType = 'danger';
                    this.loading = false;
                }
            );
    }
}