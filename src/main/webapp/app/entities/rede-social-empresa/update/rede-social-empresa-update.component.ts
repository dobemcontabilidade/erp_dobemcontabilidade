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
import { IRedeSocial } from 'app/entities/rede-social/rede-social.model';
import { RedeSocialService } from 'app/entities/rede-social/service/rede-social.service';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { RedeSocialEmpresaService } from '../service/rede-social-empresa.service';
import { IRedeSocialEmpresa } from '../rede-social-empresa.model';
import { RedeSocialEmpresaFormService, RedeSocialEmpresaFormGroup } from './rede-social-empresa-form.service';

@Component({
  standalone: true,
  selector: 'jhi-rede-social-empresa-update',
  templateUrl: './rede-social-empresa-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RedeSocialEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  redeSocialEmpresa: IRedeSocialEmpresa | null = null;

  redeSocialsSharedCollection: IRedeSocial[] = [];
  pessoajuridicasSharedCollection: IPessoajuridica[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected redeSocialEmpresaService = inject(RedeSocialEmpresaService);
  protected redeSocialEmpresaFormService = inject(RedeSocialEmpresaFormService);
  protected redeSocialService = inject(RedeSocialService);
  protected pessoajuridicaService = inject(PessoajuridicaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RedeSocialEmpresaFormGroup = this.redeSocialEmpresaFormService.createRedeSocialEmpresaFormGroup();

  compareRedeSocial = (o1: IRedeSocial | null, o2: IRedeSocial | null): boolean => this.redeSocialService.compareRedeSocial(o1, o2);

  comparePessoajuridica = (o1: IPessoajuridica | null, o2: IPessoajuridica | null): boolean =>
    this.pessoajuridicaService.comparePessoajuridica(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ redeSocialEmpresa }) => {
      this.redeSocialEmpresa = redeSocialEmpresa;
      if (redeSocialEmpresa) {
        this.updateForm(redeSocialEmpresa);
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
    const redeSocialEmpresa = this.redeSocialEmpresaFormService.getRedeSocialEmpresa(this.editForm);
    if (redeSocialEmpresa.id !== null) {
      this.subscribeToSaveResponse(this.redeSocialEmpresaService.update(redeSocialEmpresa));
    } else {
      this.subscribeToSaveResponse(this.redeSocialEmpresaService.create(redeSocialEmpresa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRedeSocialEmpresa>>): void {
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

  protected updateForm(redeSocialEmpresa: IRedeSocialEmpresa): void {
    this.redeSocialEmpresa = redeSocialEmpresa;
    this.redeSocialEmpresaFormService.resetForm(this.editForm, redeSocialEmpresa);

    this.redeSocialsSharedCollection = this.redeSocialService.addRedeSocialToCollectionIfMissing<IRedeSocial>(
      this.redeSocialsSharedCollection,
      redeSocialEmpresa.redeSocial,
    );
    this.pessoajuridicasSharedCollection = this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
      this.pessoajuridicasSharedCollection,
      redeSocialEmpresa.pessoajuridica,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.redeSocialService
      .query()
      .pipe(map((res: HttpResponse<IRedeSocial[]>) => res.body ?? []))
      .pipe(
        map((redeSocials: IRedeSocial[]) =>
          this.redeSocialService.addRedeSocialToCollectionIfMissing<IRedeSocial>(redeSocials, this.redeSocialEmpresa?.redeSocial),
        ),
      )
      .subscribe((redeSocials: IRedeSocial[]) => (this.redeSocialsSharedCollection = redeSocials));

    this.pessoajuridicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoajuridica[]>) => res.body ?? []))
      .pipe(
        map((pessoajuridicas: IPessoajuridica[]) =>
          this.pessoajuridicaService.addPessoajuridicaToCollectionIfMissing<IPessoajuridica>(
            pessoajuridicas,
            this.redeSocialEmpresa?.pessoajuridica,
          ),
        ),
      )
      .subscribe((pessoajuridicas: IPessoajuridica[]) => (this.pessoajuridicasSharedCollection = pessoajuridicas));
  }
}
