import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPais } from 'app/entities/pais/pais.model';
import { PaisService } from 'app/entities/pais/service/pais.service';
import { IEstado } from '../estado.model';
import { EstadoService } from '../service/estado.service';
import { EstadoFormService, EstadoFormGroup } from './estado-form.service';

@Component({
  standalone: true,
  selector: 'jhi-estado-update',
  templateUrl: './estado-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EstadoUpdateComponent implements OnInit {
  isSaving = false;
  estado: IEstado | null = null;

  paisSharedCollection: IPais[] = [];

  protected estadoService = inject(EstadoService);
  protected estadoFormService = inject(EstadoFormService);
  protected paisService = inject(PaisService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EstadoFormGroup = this.estadoFormService.createEstadoFormGroup();

  comparePais = (o1: IPais | null, o2: IPais | null): boolean => this.paisService.comparePais(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estado }) => {
      this.estado = estado;
      if (estado) {
        this.updateForm(estado);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estado = this.estadoFormService.getEstado(this.editForm);
    if (estado.id !== null) {
      this.subscribeToSaveResponse(this.estadoService.update(estado));
    } else {
      this.subscribeToSaveResponse(this.estadoService.create(estado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstado>>): void {
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

  protected updateForm(estado: IEstado): void {
    this.estado = estado;
    this.estadoFormService.resetForm(this.editForm, estado);

    this.paisSharedCollection = this.paisService.addPaisToCollectionIfMissing<IPais>(this.paisSharedCollection, estado.pais);
  }

  protected loadRelationshipsOptions(): void {
    this.paisService
      .query()
      .pipe(map((res: HttpResponse<IPais[]>) => res.body ?? []))
      .pipe(map((pais: IPais[]) => this.paisService.addPaisToCollectionIfMissing<IPais>(pais, this.estado?.pais)))
      .subscribe((pais: IPais[]) => (this.paisSharedCollection = pais));
  }
}
