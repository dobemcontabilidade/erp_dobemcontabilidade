import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IFluxoModelo } from '../fluxo-modelo.model';
import { FluxoModeloService } from '../service/fluxo-modelo.service';
import { FluxoModeloFormService, FluxoModeloFormGroup } from './fluxo-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-fluxo-modelo-update',
  templateUrl: './fluxo-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FluxoModeloUpdateComponent implements OnInit {
  isSaving = false;
  fluxoModelo: IFluxoModelo | null = null;

  cidadesSharedCollection: ICidade[] = [];

  protected fluxoModeloService = inject(FluxoModeloService);
  protected fluxoModeloFormService = inject(FluxoModeloFormService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FluxoModeloFormGroup = this.fluxoModeloFormService.createFluxoModeloFormGroup();

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fluxoModelo }) => {
      this.fluxoModelo = fluxoModelo;
      if (fluxoModelo) {
        this.updateForm(fluxoModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fluxoModelo = this.fluxoModeloFormService.getFluxoModelo(this.editForm);
    if (fluxoModelo.id !== null) {
      this.subscribeToSaveResponse(this.fluxoModeloService.update(fluxoModelo));
    } else {
      this.subscribeToSaveResponse(this.fluxoModeloService.create(fluxoModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFluxoModelo>>): void {
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

  protected updateForm(fluxoModelo: IFluxoModelo): void {
    this.fluxoModelo = fluxoModelo;
    this.fluxoModeloFormService.resetForm(this.editForm, fluxoModelo);

    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      fluxoModelo.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.fluxoModelo?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
