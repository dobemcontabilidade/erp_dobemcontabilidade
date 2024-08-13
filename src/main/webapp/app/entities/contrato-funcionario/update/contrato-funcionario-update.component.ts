import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario/service/funcionario.service';
import { IAgenteIntegracaoEstagio } from 'app/entities/agente-integracao-estagio/agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioService } from 'app/entities/agente-integracao-estagio/service/agente-integracao-estagio.service';
import { IInstituicaoEnsino } from 'app/entities/instituicao-ensino/instituicao-ensino.model';
import { InstituicaoEnsinoService } from 'app/entities/instituicao-ensino/service/instituicao-ensino.service';
import { NaturezaEstagioEnum } from 'app/entities/enumerations/natureza-estagio-enum.model';
import { SituacaoFuncionarioEnum } from 'app/entities/enumerations/situacao-funcionario-enum.model';
import { CategoriaTrabalhadorEnum } from 'app/entities/enumerations/categoria-trabalhador-enum.model';
import { TipoVinculoTrabalhoEnum } from 'app/entities/enumerations/tipo-vinculo-trabalho-enum.model';
import { FgtsOpcaoEnum } from 'app/entities/enumerations/fgts-opcao-enum.model';
import { TipoDocumentoEnum } from 'app/entities/enumerations/tipo-documento-enum.model';
import { PeriodoExperienciaEnum } from 'app/entities/enumerations/periodo-experiencia-enum.model';
import { TipoAdmisaoEnum } from 'app/entities/enumerations/tipo-admisao-enum.model';
import { PeriodoIntermitenteEnum } from 'app/entities/enumerations/periodo-intermitente-enum.model';
import { IndicativoAdmissaoEnum } from 'app/entities/enumerations/indicativo-admissao-enum.model';
import { ContratoFuncionarioService } from '../service/contrato-funcionario.service';
import { IContratoFuncionario } from '../contrato-funcionario.model';
import { ContratoFuncionarioFormService, ContratoFuncionarioFormGroup } from './contrato-funcionario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contrato-funcionario-update',
  templateUrl: './contrato-funcionario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContratoFuncionarioUpdateComponent implements OnInit {
  isSaving = false;
  contratoFuncionario: IContratoFuncionario | null = null;
  naturezaEstagioEnumValues = Object.keys(NaturezaEstagioEnum);
  situacaoFuncionarioEnumValues = Object.keys(SituacaoFuncionarioEnum);
  categoriaTrabalhadorEnumValues = Object.keys(CategoriaTrabalhadorEnum);
  tipoVinculoTrabalhoEnumValues = Object.keys(TipoVinculoTrabalhoEnum);
  fgtsOpcaoEnumValues = Object.keys(FgtsOpcaoEnum);
  tipoDocumentoEnumValues = Object.keys(TipoDocumentoEnum);
  periodoExperienciaEnumValues = Object.keys(PeriodoExperienciaEnum);
  tipoAdmisaoEnumValues = Object.keys(TipoAdmisaoEnum);
  periodoIntermitenteEnumValues = Object.keys(PeriodoIntermitenteEnum);
  indicativoAdmissaoEnumValues = Object.keys(IndicativoAdmissaoEnum);

  funcionariosSharedCollection: IFuncionario[] = [];
  agenteIntegracaoEstagiosSharedCollection: IAgenteIntegracaoEstagio[] = [];
  instituicaoEnsinosSharedCollection: IInstituicaoEnsino[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected contratoFuncionarioService = inject(ContratoFuncionarioService);
  protected contratoFuncionarioFormService = inject(ContratoFuncionarioFormService);
  protected funcionarioService = inject(FuncionarioService);
  protected agenteIntegracaoEstagioService = inject(AgenteIntegracaoEstagioService);
  protected instituicaoEnsinoService = inject(InstituicaoEnsinoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContratoFuncionarioFormGroup = this.contratoFuncionarioFormService.createContratoFuncionarioFormGroup();

  compareFuncionario = (o1: IFuncionario | null, o2: IFuncionario | null): boolean => this.funcionarioService.compareFuncionario(o1, o2);

  compareAgenteIntegracaoEstagio = (o1: IAgenteIntegracaoEstagio | null, o2: IAgenteIntegracaoEstagio | null): boolean =>
    this.agenteIntegracaoEstagioService.compareAgenteIntegracaoEstagio(o1, o2);

  compareInstituicaoEnsino = (o1: IInstituicaoEnsino | null, o2: IInstituicaoEnsino | null): boolean =>
    this.instituicaoEnsinoService.compareInstituicaoEnsino(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contratoFuncionario }) => {
      this.contratoFuncionario = contratoFuncionario;
      if (contratoFuncionario) {
        this.updateForm(contratoFuncionario);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('erpDobemcontabilidadeApp.error', { ...err, key: 'error.file.' + err.key }),
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contratoFuncionario = this.contratoFuncionarioFormService.getContratoFuncionario(this.editForm);
    if (contratoFuncionario.id !== null) {
      this.subscribeToSaveResponse(this.contratoFuncionarioService.update(contratoFuncionario));
    } else {
      this.subscribeToSaveResponse(this.contratoFuncionarioService.create(contratoFuncionario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContratoFuncionario>>): void {
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

  protected updateForm(contratoFuncionario: IContratoFuncionario): void {
    this.contratoFuncionario = contratoFuncionario;
    this.contratoFuncionarioFormService.resetForm(this.editForm, contratoFuncionario);

    this.funcionariosSharedCollection = this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(
      this.funcionariosSharedCollection,
      contratoFuncionario.funcionario,
    );
    this.agenteIntegracaoEstagiosSharedCollection =
      this.agenteIntegracaoEstagioService.addAgenteIntegracaoEstagioToCollectionIfMissing<IAgenteIntegracaoEstagio>(
        this.agenteIntegracaoEstagiosSharedCollection,
        contratoFuncionario.agenteIntegracaoEstagio,
      );
    this.instituicaoEnsinosSharedCollection = this.instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing<IInstituicaoEnsino>(
      this.instituicaoEnsinosSharedCollection,
      contratoFuncionario.instituicaoEnsino,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.funcionarioService
      .query()
      .pipe(map((res: HttpResponse<IFuncionario[]>) => res.body ?? []))
      .pipe(
        map((funcionarios: IFuncionario[]) =>
          this.funcionarioService.addFuncionarioToCollectionIfMissing<IFuncionario>(funcionarios, this.contratoFuncionario?.funcionario),
        ),
      )
      .subscribe((funcionarios: IFuncionario[]) => (this.funcionariosSharedCollection = funcionarios));

    this.agenteIntegracaoEstagioService
      .query()
      .pipe(map((res: HttpResponse<IAgenteIntegracaoEstagio[]>) => res.body ?? []))
      .pipe(
        map((agenteIntegracaoEstagios: IAgenteIntegracaoEstagio[]) =>
          this.agenteIntegracaoEstagioService.addAgenteIntegracaoEstagioToCollectionIfMissing<IAgenteIntegracaoEstagio>(
            agenteIntegracaoEstagios,
            this.contratoFuncionario?.agenteIntegracaoEstagio,
          ),
        ),
      )
      .subscribe(
        (agenteIntegracaoEstagios: IAgenteIntegracaoEstagio[]) =>
          (this.agenteIntegracaoEstagiosSharedCollection = agenteIntegracaoEstagios),
      );

    this.instituicaoEnsinoService
      .query()
      .pipe(map((res: HttpResponse<IInstituicaoEnsino[]>) => res.body ?? []))
      .pipe(
        map((instituicaoEnsinos: IInstituicaoEnsino[]) =>
          this.instituicaoEnsinoService.addInstituicaoEnsinoToCollectionIfMissing<IInstituicaoEnsino>(
            instituicaoEnsinos,
            this.contratoFuncionario?.instituicaoEnsino,
          ),
        ),
      )
      .subscribe((instituicaoEnsinos: IInstituicaoEnsino[]) => (this.instituicaoEnsinosSharedCollection = instituicaoEnsinos));
  }
}
