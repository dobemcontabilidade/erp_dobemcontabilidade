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
import { ISocio } from 'app/entities/socio/socio.model';
import { SocioService } from 'app/entities/socio/service/socio.service';
import { ProfissaoService } from '../service/profissao.service';
import { IProfissao } from '../profissao.model';
import { ProfissaoFormService, ProfissaoFormGroup } from './profissao-form.service';

@Component({
  standalone: true,
  selector: 'jhi-profissao-update',
  templateUrl: './profissao-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProfissaoUpdateComponent implements OnInit {
  isSaving = false;
  profissao: IProfissao | null = null;

  sociosSharedCollection: ISocio[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected profissaoService = inject(ProfissaoService);
  protected profissaoFormService = inject(ProfissaoFormService);
  protected socioService = inject(SocioService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProfissaoFormGroup = this.profissaoFormService.createProfissaoFormGroup();

  compareSocio = (o1: ISocio | null, o2: ISocio | null): boolean => this.socioService.compareSocio(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profissao }) => {
      this.profissao = profissao;
      if (profissao) {
        this.updateForm(profissao);
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
    const profissao = this.profissaoFormService.getProfissao(this.editForm);
    if (profissao.id !== null) {
      this.subscribeToSaveResponse(this.profissaoService.update(profissao));
    } else {
      this.subscribeToSaveResponse(this.profissaoService.create(profissao));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfissao>>): void {
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

  protected updateForm(profissao: IProfissao): void {
    this.profissao = profissao;
    this.profissaoFormService.resetForm(this.editForm, profissao);

    this.sociosSharedCollection = this.socioService.addSocioToCollectionIfMissing<ISocio>(this.sociosSharedCollection, profissao.socio);
  }

  protected loadRelationshipsOptions(): void {
    this.socioService
      .query()
      .pipe(map((res: HttpResponse<ISocio[]>) => res.body ?? []))
      .pipe(map((socios: ISocio[]) => this.socioService.addSocioToCollectionIfMissing<ISocio>(socios, this.profissao?.socio)))
      .subscribe((socios: ISocio[]) => (this.sociosSharedCollection = socios));
  }
}
