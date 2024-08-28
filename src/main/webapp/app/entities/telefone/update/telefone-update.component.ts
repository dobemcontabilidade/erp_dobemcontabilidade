import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { TipoTelefoneEnum } from 'app/entities/enumerations/tipo-telefone-enum.model';
import { TelefoneService } from '../service/telefone.service';
import { ITelefone } from '../telefone.model';
import { TelefoneFormService, TelefoneFormGroup } from './telefone-form.service';

@Component({
  standalone: true,
  selector: 'jhi-telefone-update',
  templateUrl: './telefone-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TelefoneUpdateComponent implements OnInit {
  isSaving = false;
  telefone: ITelefone | null = null;
  tipoTelefoneEnumValues = Object.keys(TipoTelefoneEnum);

  pessoaFisicasSharedCollection: IPessoaFisica[] = [];

  protected telefoneService = inject(TelefoneService);
  protected telefoneFormService = inject(TelefoneFormService);
  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TelefoneFormGroup = this.telefoneFormService.createTelefoneFormGroup();

  comparePessoaFisica = (o1: IPessoaFisica | null, o2: IPessoaFisica | null): boolean =>
    this.pessoaFisicaService.comparePessoaFisica(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ telefone }) => {
      this.telefone = telefone;
      if (telefone) {
        this.updateForm(telefone);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const telefone = this.telefoneFormService.getTelefone(this.editForm);
    if (telefone.id !== null) {
      this.subscribeToSaveResponse(this.telefoneService.update(telefone));
    } else {
      this.subscribeToSaveResponse(this.telefoneService.create(telefone));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITelefone>>): void {
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

  protected updateForm(telefone: ITelefone): void {
    this.telefone = telefone;
    this.telefoneFormService.resetForm(this.editForm, telefone);

    this.pessoaFisicasSharedCollection = this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(
      this.pessoaFisicasSharedCollection,
      telefone.pessoa,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaFisicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoaFisica[]>) => res.body ?? []))
      .pipe(
        map((pessoaFisicas: IPessoaFisica[]) =>
          this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing<IPessoaFisica>(pessoaFisicas, this.telefone?.pessoa),
        ),
      )
      .subscribe((pessoaFisicas: IPessoaFisica[]) => (this.pessoaFisicasSharedCollection = pessoaFisicas));
  }
}
