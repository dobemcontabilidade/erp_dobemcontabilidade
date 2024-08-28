import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RacaECorEnum } from 'app/entities/enumerations/raca-e-cor-enum.model';
import { PessoaComDeficienciaEnum } from 'app/entities/enumerations/pessoa-com-deficiencia-enum.model';
import { EstadoCivilEnum } from 'app/entities/enumerations/estado-civil-enum.model';
import { SexoEnum } from 'app/entities/enumerations/sexo-enum.model';
import { SituacaoContadorEnum } from 'app/entities/enumerations/situacao-contador-enum.model';
import { ContadorService } from '../service/contador.service';
import { IContador } from '../contador.model';
import { ContadorFormService, ContadorFormGroup } from './contador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contador-update',
  templateUrl: './contador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContadorUpdateComponent implements OnInit {
  isSaving = false;
  contador: IContador | null = null;
  racaECorEnumValues = Object.keys(RacaECorEnum);
  pessoaComDeficienciaEnumValues = Object.keys(PessoaComDeficienciaEnum);
  estadoCivilEnumValues = Object.keys(EstadoCivilEnum);
  sexoEnumValues = Object.keys(SexoEnum);
  situacaoContadorEnumValues = Object.keys(SituacaoContadorEnum);

  protected contadorService = inject(ContadorService);
  protected contadorFormService = inject(ContadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContadorFormGroup = this.contadorFormService.createContadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contador }) => {
      this.contador = contador;
      if (contador) {
        this.updateForm(contador);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contador = this.contadorFormService.getContador(this.editForm);
    if (contador.id !== null) {
      this.subscribeToSaveResponse(this.contadorService.update(contador));
    } else {
      this.subscribeToSaveResponse(this.contadorService.create(contador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContador>>): void {
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

  protected updateForm(contador: IContador): void {
    this.contador = contador;
    this.contadorFormService.resetForm(this.editForm, contador);
  }
}
