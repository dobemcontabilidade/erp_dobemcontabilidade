import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { TributacaoService } from '../service/tributacao.service';
import { ITributacao } from '../tributacao.model';
import { TributacaoFormService } from './tributacao-form.service';

import { TributacaoUpdateComponent } from './tributacao-update.component';

describe('Tributacao Management Update Component', () => {
  let comp: TributacaoUpdateComponent;
  let fixture: ComponentFixture<TributacaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tributacaoFormService: TributacaoFormService;
  let tributacaoService: TributacaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TributacaoUpdateComponent],
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
      .overrideTemplate(TributacaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TributacaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tributacaoFormService = TestBed.inject(TributacaoFormService);
    tributacaoService = TestBed.inject(TributacaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tributacao: ITributacao = { id: 456 };

      activatedRoute.data = of({ tributacao });
      comp.ngOnInit();

      expect(comp.tributacao).toEqual(tributacao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITributacao>>();
      const tributacao = { id: 123 };
      jest.spyOn(tributacaoFormService, 'getTributacao').mockReturnValue(tributacao);
      jest.spyOn(tributacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tributacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tributacao }));
      saveSubject.complete();

      // THEN
      expect(tributacaoFormService.getTributacao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tributacaoService.update).toHaveBeenCalledWith(expect.objectContaining(tributacao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITributacao>>();
      const tributacao = { id: 123 };
      jest.spyOn(tributacaoFormService, 'getTributacao').mockReturnValue({ id: null });
      jest.spyOn(tributacaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tributacao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tributacao }));
      saveSubject.complete();

      // THEN
      expect(tributacaoFormService.getTributacao).toHaveBeenCalled();
      expect(tributacaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITributacao>>();
      const tributacao = { id: 123 };
      jest.spyOn(tributacaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tributacao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tributacaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
