import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { SistemaService } from '../service/sistema.service';
import { ISistema } from '../sistema.model';
import { SistemaFormService } from './sistema-form.service';

import { SistemaUpdateComponent } from './sistema-update.component';

describe('Sistema Management Update Component', () => {
  let comp: SistemaUpdateComponent;
  let fixture: ComponentFixture<SistemaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sistemaFormService: SistemaFormService;
  let sistemaService: SistemaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SistemaUpdateComponent],
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
      .overrideTemplate(SistemaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SistemaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sistemaFormService = TestBed.inject(SistemaFormService);
    sistemaService = TestBed.inject(SistemaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const sistema: ISistema = { id: 456 };

      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      expect(comp.sistema).toEqual(sistema);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISistema>>();
      const sistema = { id: 123 };
      jest.spyOn(sistemaFormService, 'getSistema').mockReturnValue(sistema);
      jest.spyOn(sistemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sistema }));
      saveSubject.complete();

      // THEN
      expect(sistemaFormService.getSistema).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sistemaService.update).toHaveBeenCalledWith(expect.objectContaining(sistema));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISistema>>();
      const sistema = { id: 123 };
      jest.spyOn(sistemaFormService, 'getSistema').mockReturnValue({ id: null });
      jest.spyOn(sistemaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sistema: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sistema }));
      saveSubject.complete();

      // THEN
      expect(sistemaFormService.getSistema).toHaveBeenCalled();
      expect(sistemaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISistema>>();
      const sistema = { id: 123 };
      jest.spyOn(sistemaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sistema });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sistemaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
