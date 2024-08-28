import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRedeSocial } from 'app/entities/rede-social/rede-social.model';
import { RedeSocialService } from 'app/entities/rede-social/service/rede-social.service';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { IRedeSocialEmpresa } from '../rede-social-empresa.model';
import { RedeSocialEmpresaService } from '../service/rede-social-empresa.service';
import { RedeSocialEmpresaFormService } from './rede-social-empresa-form.service';

import { RedeSocialEmpresaUpdateComponent } from './rede-social-empresa-update.component';

describe('RedeSocialEmpresa Management Update Component', () => {
  let comp: RedeSocialEmpresaUpdateComponent;
  let fixture: ComponentFixture<RedeSocialEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let redeSocialEmpresaFormService: RedeSocialEmpresaFormService;
  let redeSocialEmpresaService: RedeSocialEmpresaService;
  let redeSocialService: RedeSocialService;
  let pessoajuridicaService: PessoajuridicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RedeSocialEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RedeSocialEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RedeSocialEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    redeSocialEmpresaFormService = TestBed.inject(RedeSocialEmpresaFormService);
    redeSocialEmpresaService = TestBed.inject(RedeSocialEmpresaService);
    redeSocialService = TestBed.inject(RedeSocialService);
    pessoajuridicaService = TestBed.inject(PessoajuridicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RedeSocial query and add missing value', () => {
      const redeSocialEmpresa: IRedeSocialEmpresa = { id: 456 };
      const redeSocial: IRedeSocial = { id: 27088 };
      redeSocialEmpresa.redeSocial = redeSocial;

      const redeSocialCollection: IRedeSocial[] = [{ id: 25702 }];
      jest.spyOn(redeSocialService, 'query').mockReturnValue(of(new HttpResponse({ body: redeSocialCollection })));
      const additionalRedeSocials = [redeSocial];
      const expectedCollection: IRedeSocial[] = [...additionalRedeSocials, ...redeSocialCollection];
      jest.spyOn(redeSocialService, 'addRedeSocialToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ redeSocialEmpresa });
      comp.ngOnInit();

      expect(redeSocialService.query).toHaveBeenCalled();
      expect(redeSocialService.addRedeSocialToCollectionIfMissing).toHaveBeenCalledWith(
        redeSocialCollection,
        ...additionalRedeSocials.map(expect.objectContaining),
      );
      expect(comp.redeSocialsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pessoajuridica query and add missing value', () => {
      const redeSocialEmpresa: IRedeSocialEmpresa = { id: 456 };
      const pessoajuridica: IPessoajuridica = { id: 12088 };
      redeSocialEmpresa.pessoajuridica = pessoajuridica;

      const pessoajuridicaCollection: IPessoajuridica[] = [{ id: 17817 }];
      jest.spyOn(pessoajuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoajuridicaCollection })));
      const additionalPessoajuridicas = [pessoajuridica];
      const expectedCollection: IPessoajuridica[] = [...additionalPessoajuridicas, ...pessoajuridicaCollection];
      jest.spyOn(pessoajuridicaService, 'addPessoajuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ redeSocialEmpresa });
      comp.ngOnInit();

      expect(pessoajuridicaService.query).toHaveBeenCalled();
      expect(pessoajuridicaService.addPessoajuridicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoajuridicaCollection,
        ...additionalPessoajuridicas.map(expect.objectContaining),
      );
      expect(comp.pessoajuridicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const redeSocialEmpresa: IRedeSocialEmpresa = { id: 456 };
      const redeSocial: IRedeSocial = { id: 11608 };
      redeSocialEmpresa.redeSocial = redeSocial;
      const pessoajuridica: IPessoajuridica = { id: 7611 };
      redeSocialEmpresa.pessoajuridica = pessoajuridica;

      activatedRoute.data = of({ redeSocialEmpresa });
      comp.ngOnInit();

      expect(comp.redeSocialsSharedCollection).toContain(redeSocial);
      expect(comp.pessoajuridicasSharedCollection).toContain(pessoajuridica);
      expect(comp.redeSocialEmpresa).toEqual(redeSocialEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRedeSocialEmpresa>>();
      const redeSocialEmpresa = { id: 123 };
      jest.spyOn(redeSocialEmpresaFormService, 'getRedeSocialEmpresa').mockReturnValue(redeSocialEmpresa);
      jest.spyOn(redeSocialEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ redeSocialEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: redeSocialEmpresa }));
      saveSubject.complete();

      // THEN
      expect(redeSocialEmpresaFormService.getRedeSocialEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(redeSocialEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(redeSocialEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRedeSocialEmpresa>>();
      const redeSocialEmpresa = { id: 123 };
      jest.spyOn(redeSocialEmpresaFormService, 'getRedeSocialEmpresa').mockReturnValue({ id: null });
      jest.spyOn(redeSocialEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ redeSocialEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: redeSocialEmpresa }));
      saveSubject.complete();

      // THEN
      expect(redeSocialEmpresaFormService.getRedeSocialEmpresa).toHaveBeenCalled();
      expect(redeSocialEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRedeSocialEmpresa>>();
      const redeSocialEmpresa = { id: 123 };
      jest.spyOn(redeSocialEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ redeSocialEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(redeSocialEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRedeSocial', () => {
      it('Should forward to redeSocialService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(redeSocialService, 'compareRedeSocial');
        comp.compareRedeSocial(entity, entity2);
        expect(redeSocialService.compareRedeSocial).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePessoajuridica', () => {
      it('Should forward to pessoajuridicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoajuridicaService, 'comparePessoajuridica');
        comp.comparePessoajuridica(entity, entity2);
        expect(pessoajuridicaService.comparePessoajuridica).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
