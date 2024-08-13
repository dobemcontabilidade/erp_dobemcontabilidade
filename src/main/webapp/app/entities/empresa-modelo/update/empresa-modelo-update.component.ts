import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { SegmentoCnaeService } from 'app/entities/segmento-cnae/service/segmento-cnae.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { EmpresaModeloService } from '../service/empresa-modelo.service';
import { IEmpresaModelo } from '../empresa-modelo.model';
import { EmpresaModeloFormService, EmpresaModeloFormGroup } from './empresa-modelo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-empresa-modelo-update',
  templateUrl: './empresa-modelo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmpresaModeloUpdateComponent implements OnInit {
  isSaving = false;
  empresaModelo: IEmpresaModelo | null = null;

  segmentoCnaesSharedCollection: ISegmentoCnae[] = [];
  ramosSharedCollection: IRamo[] = [];
  enquadramentosSharedCollection: IEnquadramento[] = [];
  tributacaosSharedCollection: ITributacao[] = [];
  cidadesSharedCollection: ICidade[] = [];

  protected empresaModeloService = inject(EmpresaModeloService);
  protected empresaModeloFormService = inject(EmpresaModeloFormService);
  protected segmentoCnaeService = inject(SegmentoCnaeService);
  protected ramoService = inject(RamoService);
  protected enquadramentoService = inject(EnquadramentoService);
  protected tributacaoService = inject(TributacaoService);
  protected cidadeService = inject(CidadeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmpresaModeloFormGroup = this.empresaModeloFormService.createEmpresaModeloFormGroup();

  compareSegmentoCnae = (o1: ISegmentoCnae | null, o2: ISegmentoCnae | null): boolean =>
    this.segmentoCnaeService.compareSegmentoCnae(o1, o2);

  compareRamo = (o1: IRamo | null, o2: IRamo | null): boolean => this.ramoService.compareRamo(o1, o2);

  compareEnquadramento = (o1: IEnquadramento | null, o2: IEnquadramento | null): boolean =>
    this.enquadramentoService.compareEnquadramento(o1, o2);

  compareTributacao = (o1: ITributacao | null, o2: ITributacao | null): boolean => this.tributacaoService.compareTributacao(o1, o2);

  compareCidade = (o1: ICidade | null, o2: ICidade | null): boolean => this.cidadeService.compareCidade(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresaModelo }) => {
      this.empresaModelo = empresaModelo;
      if (empresaModelo) {
        this.updateForm(empresaModelo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresaModelo = this.empresaModeloFormService.getEmpresaModelo(this.editForm);
    if (empresaModelo.id !== null) {
      this.subscribeToSaveResponse(this.empresaModeloService.update(empresaModelo));
    } else {
      this.subscribeToSaveResponse(this.empresaModeloService.create(empresaModelo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresaModelo>>): void {
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

  protected updateForm(empresaModelo: IEmpresaModelo): void {
    this.empresaModelo = empresaModelo;
    this.empresaModeloFormService.resetForm(this.editForm, empresaModelo);

    this.segmentoCnaesSharedCollection = this.segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing<ISegmentoCnae>(
      this.segmentoCnaesSharedCollection,
      ...(empresaModelo.segmentoCnaes ?? []),
    );
    this.ramosSharedCollection = this.ramoService.addRamoToCollectionIfMissing<IRamo>(this.ramosSharedCollection, empresaModelo.ramo);
    this.enquadramentosSharedCollection = this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
      this.enquadramentosSharedCollection,
      empresaModelo.enquadramento,
    );
    this.tributacaosSharedCollection = this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(
      this.tributacaosSharedCollection,
      empresaModelo.tributacao,
    );
    this.cidadesSharedCollection = this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(
      this.cidadesSharedCollection,
      empresaModelo.cidade,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.segmentoCnaeService
      .query()
      .pipe(map((res: HttpResponse<ISegmentoCnae[]>) => res.body ?? []))
      .pipe(
        map((segmentoCnaes: ISegmentoCnae[]) =>
          this.segmentoCnaeService.addSegmentoCnaeToCollectionIfMissing<ISegmentoCnae>(
            segmentoCnaes,
            ...(this.empresaModelo?.segmentoCnaes ?? []),
          ),
        ),
      )
      .subscribe((segmentoCnaes: ISegmentoCnae[]) => (this.segmentoCnaesSharedCollection = segmentoCnaes));

    this.ramoService
      .query()
      .pipe(map((res: HttpResponse<IRamo[]>) => res.body ?? []))
      .pipe(map((ramos: IRamo[]) => this.ramoService.addRamoToCollectionIfMissing<IRamo>(ramos, this.empresaModelo?.ramo)))
      .subscribe((ramos: IRamo[]) => (this.ramosSharedCollection = ramos));

    this.enquadramentoService
      .query()
      .pipe(map((res: HttpResponse<IEnquadramento[]>) => res.body ?? []))
      .pipe(
        map((enquadramentos: IEnquadramento[]) =>
          this.enquadramentoService.addEnquadramentoToCollectionIfMissing<IEnquadramento>(
            enquadramentos,
            this.empresaModelo?.enquadramento,
          ),
        ),
      )
      .subscribe((enquadramentos: IEnquadramento[]) => (this.enquadramentosSharedCollection = enquadramentos));

    this.tributacaoService
      .query()
      .pipe(map((res: HttpResponse<ITributacao[]>) => res.body ?? []))
      .pipe(
        map((tributacaos: ITributacao[]) =>
          this.tributacaoService.addTributacaoToCollectionIfMissing<ITributacao>(tributacaos, this.empresaModelo?.tributacao),
        ),
      )
      .subscribe((tributacaos: ITributacao[]) => (this.tributacaosSharedCollection = tributacaos));

    this.cidadeService
      .query()
      .pipe(map((res: HttpResponse<ICidade[]>) => res.body ?? []))
      .pipe(map((cidades: ICidade[]) => this.cidadeService.addCidadeToCollectionIfMissing<ICidade>(cidades, this.empresaModelo?.cidade)))
      .subscribe((cidades: ICidade[]) => (this.cidadesSharedCollection = cidades));
  }
}
