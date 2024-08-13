import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { AgenteIntegracaoEstagioService } from '../service/agente-integracao-estagio.service';
import { IAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioFormService } from './agente-integracao-estagio-form.service';

import { AgenteIntegracaoEstagioUpdateComponent } from './agente-integracao-estagio-update.component';

describe('AgenteIntegracaoEstagio Management Update Component', () => {
  let comp: AgenteIntegracaoEstagioUpdateComponent;
  let fixture: ComponentFixture<AgenteIntegracaoEstagioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agenteIntegracaoEstagioFormService: AgenteIntegracaoEstagioFormService;
  let agenteIntegracaoEstagioService: AgenteIntegracaoEstagioService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgenteIntegracaoEstagioUpdateComponent],
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
      .overrideTemplate(AgenteIntegracaoEstagioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgenteIntegracaoEstagioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agenteIntegracaoEstagioFormService = TestBed.inject(AgenteIntegracaoEstagioFormService);
    agenteIntegracaoEstagioService = TestBed.inject(AgenteIntegracaoEstagioService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cidade query and add missing value', () => {
      const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = { id: 456 };
      const cidade: ICidade = { id: 3842 };
      agenteIntegracaoEstagio.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 8438 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenteIntegracaoEstagio });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agenteIntegracaoEstagio: IAgenteIntegracaoEstagio = { id: 456 };
      const cidade: ICidade = { id: 19360 };
      agenteIntegracaoEstagio.cidade = cidade;

      activatedRoute.data = of({ agenteIntegracaoEstagio });
      comp.ngOnInit();

      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.agenteIntegracaoEstagio).toEqual(agenteIntegracaoEstagio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenteIntegracaoEstagio>>();
      const agenteIntegracaoEstagio = { id: 123 };
      jest.spyOn(agenteIntegracaoEstagioFormService, 'getAgenteIntegracaoEstagio').mockReturnValue(agenteIntegracaoEstagio);
      jest.spyOn(agenteIntegracaoEstagioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenteIntegracaoEstagio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenteIntegracaoEstagio }));
      saveSubject.complete();

      // THEN
      expect(agenteIntegracaoEstagioFormService.getAgenteIntegracaoEstagio).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agenteIntegracaoEstagioService.update).toHaveBeenCalledWith(expect.objectContaining(agenteIntegracaoEstagio));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenteIntegracaoEstagio>>();
      const agenteIntegracaoEstagio = { id: 123 };
      jest.spyOn(agenteIntegracaoEstagioFormService, 'getAgenteIntegracaoEstagio').mockReturnValue({ id: null });
      jest.spyOn(agenteIntegracaoEstagioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenteIntegracaoEstagio: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenteIntegracaoEstagio }));
      saveSubject.complete();

      // THEN
      expect(agenteIntegracaoEstagioFormService.getAgenteIntegracaoEstagio).toHaveBeenCalled();
      expect(agenteIntegracaoEstagioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenteIntegracaoEstagio>>();
      const agenteIntegracaoEstagio = { id: 123 };
      jest.spyOn(agenteIntegracaoEstagioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenteIntegracaoEstagio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agenteIntegracaoEstagioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
