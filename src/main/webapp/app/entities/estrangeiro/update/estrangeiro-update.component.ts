import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IEstrangeiro } from '../estrangeiro.model';
import { EstrangeiroService } from '../service/estrangeiro.service';
import { EstrangeiroFormService, EstrangeiroFormGroup } from './estrangeiro-form.service';

@Component({
  standalone: true,
  selector: 'jhi-estrangeiro-update',
  templateUrl: './estrangeiro-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EstrangeiroUpdateComponent implements OnInit {
  isSaving = false;
  estrangeiro: IEstrangeiro | null = null;

  funcionariosSharedCollection: IFuncionario[] = [];

  protected estrangeiroService = inject(EstrangeiroService);
  protected estrangeiroFormService = inject(EstrangeiroFormService);
  protected funcionarioService = inject(FuncionarioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EstrangeiroFormGroup = this.estrangeiroFormService.createEstrangeiroFormGroup();

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estrangeiro }) => {
      this.estrangeiro = estrangeiro;
      if (estrangeiro) {
        this.updateForm(estrangeiro);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estrangeiro = this.estrangeiroFormService.getEstrangeiro(this.editForm);
    if (estrangeiro.id !== null) {
      this.subscribeToSaveResponse(this.estrangeiroService.update(estrangeiro));
    } else {
      this.subscribeToSaveResponse(this.estrangeiroService.create(estrangeiro));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstrangeiro>>): void {
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

  protected updateForm(estrangeiro: IEstrangeiro): void {
    this.estrangeiro = estrangeiro;
    this.estrangeiroFormService.resetForm(this.editForm, estrangeiro);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      estrangeiro.funcionario,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.estrangeiro?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));
  }
}
