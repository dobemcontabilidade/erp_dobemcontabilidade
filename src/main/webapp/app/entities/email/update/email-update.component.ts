import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { IEmail } from '../email.model';
import { EmailService } from '../service/email.service';
import { EmailFormService, EmailFormGroup } from './email-form.service';

@Component({
  standalone: true,
  selector: 'jhi-email-update',
  templateUrl: './email-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmailUpdateComponent implements OnInit {
  isSaving = false;
  email: IEmail | null = null;

  pessoaFisicasSharedCollection: IPessoaFisica[] = [];

  protected emailService = inject(EmailService);
  protected emailFormService = inject(EmailFormService);
  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmailFormGroup = this.emailFormService.createEmailFormGroup();

  comparePessoaFisica = (o1: IPessoaFisica | null, o2: IPessoaFisica | null): boolean =>
    this.pessoaFisicaService.comparePessoaFisica(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ email }) => {
      this.email = email;
      if (email) {
        this.updateForm(email);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const email = this.emailFormService.getEmail(this.editForm);
    if (email.id !== null) {
      this.subscribeToSaveResponse(this.emailService.update(email));
    } else {
      this.subscribeToSaveResponse(this.emailService.create(email));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmail>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(email: IEmail): void {
    this.email = email;
    this.emailFormService.resetForm(this.editForm, email);

    this.pessoaFisicasSharedCollection = this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(
      this.pessoaFisicasSharedCollection,
      email.pessoa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaFisicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoaFisica[]>) => res.body ?? []))
      .pipe(
        map((pessoaFisicas: IPessoaFisica[]) =>
          this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(pessoaFisicas, this.email?.pessoa),
        ),
      )
      .subscribe((pessoaFisicas: IPessoaFisica[]) => (this.pessoaFisicasSharedCollection = pessoaFisicas));
  }
}
