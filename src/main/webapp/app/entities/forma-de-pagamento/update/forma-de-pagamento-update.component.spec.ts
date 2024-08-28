import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { FormaDePagamentoService } from '../service/forma-de-pagamento.service';
import { IFormaDePagamento } from '../forma-de-pagamento.model';
import { FormaDePagamentoFormService } from './forma-de-pagamento-form.service';

import { FormaDePagamentoUpdateComponent } from './forma-de-pagamento-update.component';

describe('FormaDePagamento Management Update Component', () => {
  let comp: FormaDePagamentoUpdateComponent;
  let fixture: ComponentFixture<FormaDePagamentoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let formaDePagamentoFormService: FormaDePagamentoFormService;
  let formaDePagamentoService: FormaDePagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FormaDePagamentoUpdateComponent],
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
      .overrideTemplate(FormaDePagamentoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FormaDePagamentoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    formaDePagamentoFormService = TestBed.inject(FormaDePagamentoFormService);
    formaDePagamentoService = TestBed.inject(FormaDePagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const formaDePagamento: IFormaDePagamento = { id: 456 };

      activatedRoute.data = of({ formaDePagamento });
      comp.ngOnInit();

      expect(comp.formaDePagamento).toEqual(formaDePagamento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormaDePagamento>>();
      const formaDePagamento = { id: 123 };
      jest.spyOn(formaDePagamentoFormService, 'getFormaDePagamento').mockReturnValue(formaDePagamento);
      jest.spyOn(formaDePagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formaDePagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formaDePagamento }));
      saveSubject.complete();

      // THEN
      expect(formaDePagamentoFormService.getFormaDePagamento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(formaDePagamentoService.update).toHaveBeenCalledWith(expect.objectContaining(formaDePagamento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormaDePagamento>>();
      const formaDePagamento = { id: 123 };
      jest.spyOn(formaDePagamentoFormService, 'getFormaDePagamento').mockReturnValue({ id: null });
      jest.spyOn(formaDePagamentoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formaDePagamento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: formaDePagamento }));
      saveSubject.complete();

      // THEN
      expect(formaDePagamentoFormService.getFormaDePagamento).toHaveBeenCalled();
      expect(formaDePagamentoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFormaDePagamento>>();
      const formaDePagamento = { id: 123 };
      jest.spyOn(formaDePagamentoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ formaDePagamento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(formaDePagamentoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
