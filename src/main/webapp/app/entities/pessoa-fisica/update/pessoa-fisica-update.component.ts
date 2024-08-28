import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EstadoCivilEnum } from 'app/entities/enumerations/estado-civil-enum.model';
import { SexoEnum } from 'app/entities/enumerations/sexo-enum.model';
import { IPessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaService } from '../service/pessoa-fisica.service';
import { PessoaFisicaFormService, PessoaFisicaFormGroup } from './pessoa-fisica-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pessoa-fisica-update',
  templateUrl: './pessoa-fisica-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PessoaFisicaUpdateComponent implements OnInit {
  isSaving = false;
  pessoaFisica: IPessoaFisica | null = null;
  estadoCivilEnumValues = Object.keys(EstadoCivilEnum);
  sexoEnumValues = Object.keys(SexoEnum);

  protected pessoaFisicaService = inject(PessoaFisicaService);
  protected pessoaFisicaFormService = inject(PessoaFisicaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PessoaFisicaFormGroup = this.pessoaFisicaFormService.createPessoaFisicaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaFisica }) => {
      this.pessoaFisica = pessoaFisica;
      if (pessoaFisica) {
        this.updateForm(pessoaFisica);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoaFisica = this.pessoaFisicaFormService.getPessoaFisica(this.editForm);
    if (pessoaFisica.id !== null) {
      this.subscribeToSaveResponse(this.pessoaFisicaService.update(pessoaFisica));
    } else {
      this.subscribeToSaveResponse(this.pessoaFisicaService.create(pessoaFisica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoaFisica>>): void {
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

  protected updateForm(pessoaFisica: IPessoaFisica): void {
    this.pessoaFisica = pessoaFisica;
    this.pessoaFisicaFormService.resetForm(this.editForm, pessoaFisica);
  }
}
