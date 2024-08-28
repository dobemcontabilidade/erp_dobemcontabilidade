import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from 'app/entities/plano-assinatura-contabil/service/plano-assinatura-contabil.service';
import { IAdicionalTributacao } from '../adicional-tributacao.model';
import { AdicionalTributacaoService } from '../service/adicional-tributacao.service';
import { AdicionalTributacaoFormService } from './adicional-tributacao-form.service';

import { AdicionalTributacaoUpdateComponent } from './adicional-tributacao-update.component';

describe('AdicionalTributacao Management Update Component', () => {
  let comp: AdicionalTributacaoUpdateComponent;
  let fixture: ComponentFixture<AdicionalTributacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let adicionalTributacaoFormService: AdicionalTributacaoFormService;
  let adicionalTributacaoService: AdicionalTributacaoService;
  let tributacaoService: TributacaoService;
  let planoAssinaturaContabilService: PlanoAssinaturaContabilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AdicionalTributacaoUpdateComponent],
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
      .overrideTemplate(AdicionalTributacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdicionalTributacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    adicionalTributacaoFormService = TestBed.inject(AdicionalTributacaoFormService);
    adicionalTributacaoService = TestBed.inject(AdicionalTributacaoService);
    tributacaoService = TestBed.inject(TributacaoService);
    planoAssinaturaContabilService = TestBed.inject(PlanoAssinaturaContabilService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Tributacao query and add missing value', () => {
      const adicionalTributacao: IAdicionalTributacao = { id: 456 };
      const tributacao: ITributacao = { id: 32426 };
      adicionalTributacao.tributacao = tributacao;

      const tributacaoCollection: ITributacao[] = [{ id: 15443 }];
      jest.spyOn(tributacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tributacaoCollection })));
      const additionalTributacaos = [tributacao];
      const expectedCollection: ITributacao[] = [...additionalTributacaos, ...tributacaoCollection];
      jest.spyOn(tributacaoService, 'addTributacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adicionalTributacao });
      comp.ngOnInit();

      expect(tributacaoService.query).toHaveBeenCalled();
      expect(tributacaoService.addTributacaoToCollectionIfMissing).toHaveBeenCalledWith(
        tributacaoCollection,
        ...additionalTributacaos.map(expect.objectContaining),
      );
      expect(comp.tributacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoAssinaturaContabil query and add missing value', () => {
      const adicionalTributacao: IAdicionalTributacao = { id: 456 };
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 5020 };
      adicionalTributacao.planoAssinaturaContabil = planoAssinaturaContabil;

      const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [{ id: 11446 }];
      jest
        .spyOn(planoAssinaturaContabilService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: planoAssinaturaContabilCollection })));
      const additionalPlanoAssinaturaContabils = [planoAssinaturaContabil];
      const expectedCollection: IPlanoAssinaturaContabil[] = [...additionalPlanoAssinaturaContabils, ...planoAssinaturaContabilCollection];
      jest.spyOn(planoAssinaturaContabilService, 'addPlanoAssinaturaContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ adicionalTributacao });
      comp.ngOnInit();

      expect(planoAssinaturaContabilService.query).toHaveBeenCalled();
      expect(planoAssinaturaContabilService.addPlanoAssinaturaContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoAssinaturaContabilCollection,
        ...additionalPlanoAssinaturaContabils.map(expect.objectContaining),
      );
      expect(comp.planoAssinaturaContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const adicionalTributacao: IAdicionalTributacao = { id: 456 };
      const tributacao: ITributacao = { id: 11958 };
      adicionalTributacao.tributacao = tributacao;
      const planoAssinaturaContabil: IPlanoAssinaturaContabil = { id: 32619 };
      adicionalTributacao.planoAssinaturaContabil = planoAssinaturaContabil;

      activatedRoute.data = of({ adicionalTributacao });
      comp.ngOnInit();

      expect(comp.tributacaosSharedCollection).toContain(tributacao);
      expect(comp.planoAssinaturaContabilsSharedCollection).toContain(planoAssinaturaContabil);
      expect(comp.adicionalTributacao).toEqual(adicionalTributacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalTributacao>>();
      const adicionalTributacao = { id: 123 };
      jest.spyOn(adicionalTributacaoFormService, 'getAdicionalTributacao').mockReturnValue(adicionalTributacao);
      jest.spyOn(adicionalTributacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalTributacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adicionalTributacao }));
      saveSubject.complete();

      // THEN
      expect(adicionalTributacaoFormService.getAdicionalTributacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(adicionalTributacaoService.update).toHaveBeenCalledWith(expect.objectContaining(adicionalTributacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalTributacao>>();
      const adicionalTributacao = { id: 123 };
      jest.spyOn(adicionalTributacaoFormService, 'getAdicionalTributacao').mockReturnValue({ id: null });
      jest.spyOn(adicionalTributacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalTributacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: adicionalTributacao }));
      saveSubject.complete();

      // THEN
      expect(adicionalTributacaoFormService.getAdicionalTributacao).toHaveBeenCalled();
      expect(adicionalTributacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdicionalTributacao>>();
      const adicionalTributacao = { id: 123 };
      jest.spyOn(adicionalTributacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ adicionalTributacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(adicionalTributacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTributacao', () => {
      it('Should forward to tributacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tributacaoService, 'compareTributacao');
        comp.compareTributacao(entity, entity2);
        expect(tributacaoService.compareTributacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoAssinaturaContabil', () => {
      it('Should forward to planoAssinaturaContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoAssinaturaContabilService, 'comparePlanoAssinaturaContabil');
        comp.comparePlanoAssinaturaContabil(entity, entity2);
        expect(planoAssinaturaContabilService.comparePlanoAssinaturaContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
