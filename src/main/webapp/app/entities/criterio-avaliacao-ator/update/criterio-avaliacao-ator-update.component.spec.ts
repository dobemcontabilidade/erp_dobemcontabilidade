import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICriterioAvaliacao } from 'app/entities/criterio-avaliacao/criterio-avaliacao.model';
import { CriterioAvaliacaoService } from 'app/entities/criterio-avaliacao/service/criterio-avaliacao.service';
import { IAtorAvaliado } from 'app/entities/ator-avaliado/ator-avaliado.model';
import { AtorAvaliadoService } from 'app/entities/ator-avaliado/service/ator-avaliado.service';
import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from '../service/criterio-avaliacao-ator.service';
import { CriterioAvaliacaoAtorFormService } from './criterio-avaliacao-ator-form.service';

import { CriterioAvaliacaoAtorUpdateComponent } from './criterio-avaliacao-ator-update.component';

describe('CriterioAvaliacaoAtor Management Update Component', () => {
  let comp: CriterioAvaliacaoAtorUpdateComponent;
  let fixture: ComponentFixture<CriterioAvaliacaoAtorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let criterioAvaliacaoAtorFormService: CriterioAvaliacaoAtorFormService;
  let criterioAvaliacaoAtorService: CriterioAvaliacaoAtorService;
  let criterioAvaliacaoService: CriterioAvaliacaoService;
  let atorAvaliadoService: AtorAvaliadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CriterioAvaliacaoAtorUpdateComponent],
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
      .overrideTemplate(CriterioAvaliacaoAtorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CriterioAvaliacaoAtorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    criterioAvaliacaoAtorFormService = TestBed.inject(CriterioAvaliacaoAtorFormService);
    criterioAvaliacaoAtorService = TestBed.inject(CriterioAvaliacaoAtorService);
    criterioAvaliacaoService = TestBed.inject(CriterioAvaliacaoService);
    atorAvaliadoService = TestBed.inject(AtorAvaliadoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CriterioAvaliacao query and add missing value', () => {
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 456 };
      const criterioAvaliacao: ICriterioAvaliacao = { id: 27274 };
      criterioAvaliacaoAtor.criterioAvaliacao = criterioAvaliacao;

      const criterioAvaliacaoCollection: ICriterioAvaliacao[] = [{ id: 1550 }];
      jest.spyOn(criterioAvaliacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: criterioAvaliacaoCollection })));
      const additionalCriterioAvaliacaos = [criterioAvaliacao];
      const expectedCollection: ICriterioAvaliacao[] = [...additionalCriterioAvaliacaos, ...criterioAvaliacaoCollection];
      jest.spyOn(criterioAvaliacaoService, 'addCriterioAvaliacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ criterioAvaliacaoAtor });
      comp.ngOnInit();

      expect(criterioAvaliacaoService.query).toHaveBeenCalled();
      expect(criterioAvaliacaoService.addCriterioAvaliacaoToCollectionIfMissing).toHaveBeenCalledWith(
        criterioAvaliacaoCollection,
        ...additionalCriterioAvaliacaos.map(expect.objectContaining),
      );
      expect(comp.criterioAvaliacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AtorAvaliado query and add missing value', () => {
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 456 };
      const atorAvaliado: IAtorAvaliado = { id: 16004 };
      criterioAvaliacaoAtor.atorAvaliado = atorAvaliado;

      const atorAvaliadoCollection: IAtorAvaliado[] = [{ id: 20612 }];
      jest.spyOn(atorAvaliadoService, 'query').mockReturnValue(of(new HttpResponse({ body: atorAvaliadoCollection })));
      const additionalAtorAvaliados = [atorAvaliado];
      const expectedCollection: IAtorAvaliado[] = [...additionalAtorAvaliados, ...atorAvaliadoCollection];
      jest.spyOn(atorAvaliadoService, 'addAtorAvaliadoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ criterioAvaliacaoAtor });
      comp.ngOnInit();

      expect(atorAvaliadoService.query).toHaveBeenCalled();
      expect(atorAvaliadoService.addAtorAvaliadoToCollectionIfMissing).toHaveBeenCalledWith(
        atorAvaliadoCollection,
        ...additionalAtorAvaliados.map(expect.objectContaining),
      );
      expect(comp.atorAvaliadosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = { id: 456 };
      const criterioAvaliacao: ICriterioAvaliacao = { id: 22727 };
      criterioAvaliacaoAtor.criterioAvaliacao = criterioAvaliacao;
      const atorAvaliado: IAtorAvaliado = { id: 29564 };
      criterioAvaliacaoAtor.atorAvaliado = atorAvaliado;

      activatedRoute.data = of({ criterioAvaliacaoAtor });
      comp.ngOnInit();

      expect(comp.criterioAvaliacaosSharedCollection).toContain(criterioAvaliacao);
      expect(comp.atorAvaliadosSharedCollection).toContain(atorAvaliado);
      expect(comp.criterioAvaliacaoAtor).toEqual(criterioAvaliacaoAtor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriterioAvaliacaoAtor>>();
      const criterioAvaliacaoAtor = { id: 123 };
      jest.spyOn(criterioAvaliacaoAtorFormService, 'getCriterioAvaliacaoAtor').mockReturnValue(criterioAvaliacaoAtor);
      jest.spyOn(criterioAvaliacaoAtorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criterioAvaliacaoAtor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criterioAvaliacaoAtor }));
      saveSubject.complete();

      // THEN
      expect(criterioAvaliacaoAtorFormService.getCriterioAvaliacaoAtor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(criterioAvaliacaoAtorService.update).toHaveBeenCalledWith(expect.objectContaining(criterioAvaliacaoAtor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriterioAvaliacaoAtor>>();
      const criterioAvaliacaoAtor = { id: 123 };
      jest.spyOn(criterioAvaliacaoAtorFormService, 'getCriterioAvaliacaoAtor').mockReturnValue({ id: null });
      jest.spyOn(criterioAvaliacaoAtorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criterioAvaliacaoAtor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criterioAvaliacaoAtor }));
      saveSubject.complete();

      // THEN
      expect(criterioAvaliacaoAtorFormService.getCriterioAvaliacaoAtor).toHaveBeenCalled();
      expect(criterioAvaliacaoAtorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriterioAvaliacaoAtor>>();
      const criterioAvaliacaoAtor = { id: 123 };
      jest.spyOn(criterioAvaliacaoAtorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criterioAvaliacaoAtor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(criterioAvaliacaoAtorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCriterioAvaliacao', () => {
      it('Should forward to criterioAvaliacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(criterioAvaliacaoService, 'compareCriterioAvaliacao');
        comp.compareCriterioAvaliacao(entity, entity2);
        expect(criterioAvaliacaoService.compareCriterioAvaliacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAtorAvaliado', () => {
      it('Should forward to atorAvaliadoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(atorAvaliadoService, 'compareAtorAvaliado');
        comp.compareAtorAvaliado(entity, entity2);
        expect(atorAvaliadoService.compareAtorAvaliado).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
