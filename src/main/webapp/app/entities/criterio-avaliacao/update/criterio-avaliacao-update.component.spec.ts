import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CriterioAvaliacaoService } from '../service/criterio-avaliacao.service';
import { ICriterioAvaliacao } from '../criterio-avaliacao.model';
import { CriterioAvaliacaoFormService } from './criterio-avaliacao-form.service';

import { CriterioAvaliacaoUpdateComponent } from './criterio-avaliacao-update.component';

describe('CriterioAvaliacao Management Update Component', () => {
  let comp: CriterioAvaliacaoUpdateComponent;
  let fixture: ComponentFixture<CriterioAvaliacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let criterioAvaliacaoFormService: CriterioAvaliacaoFormService;
  let criterioAvaliacaoService: CriterioAvaliacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CriterioAvaliacaoUpdateComponent],
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
      .overrideTemplate(CriterioAvaliacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CriterioAvaliacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    criterioAvaliacaoFormService = TestBed.inject(CriterioAvaliacaoFormService);
    criterioAvaliacaoService = TestBed.inject(CriterioAvaliacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const criterioAvaliacao: ICriterioAvaliacao = { id: 456 };

      activatedRoute.data = of({ criterioAvaliacao });
      comp.ngOnInit();

      expect(comp.criterioAvaliacao).toEqual(criterioAvaliacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriterioAvaliacao>>();
      const criterioAvaliacao = { id: 123 };
      jest.spyOn(criterioAvaliacaoFormService, 'getCriterioAvaliacao').mockReturnValue(criterioAvaliacao);
      jest.spyOn(criterioAvaliacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criterioAvaliacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criterioAvaliacao }));
      saveSubject.complete();

      // THEN
      expect(criterioAvaliacaoFormService.getCriterioAvaliacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(criterioAvaliacaoService.update).toHaveBeenCalledWith(expect.objectContaining(criterioAvaliacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriterioAvaliacao>>();
      const criterioAvaliacao = { id: 123 };
      jest.spyOn(criterioAvaliacaoFormService, 'getCriterioAvaliacao').mockReturnValue({ id: null });
      jest.spyOn(criterioAvaliacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criterioAvaliacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: criterioAvaliacao }));
      saveSubject.complete();

      // THEN
      expect(criterioAvaliacaoFormService.getCriterioAvaliacao).toHaveBeenCalled();
      expect(criterioAvaliacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICriterioAvaliacao>>();
      const criterioAvaliacao = { id: 123 };
      jest.spyOn(criterioAvaliacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ criterioAvaliacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(criterioAvaliacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
