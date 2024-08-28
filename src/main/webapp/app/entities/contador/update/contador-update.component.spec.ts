import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ContadorService } from '../service/contador.service';
import { IContador } from '../contador.model';
import { ContadorFormService } from './contador-form.service';

import { ContadorUpdateComponent } from './contador-update.component';

describe('Contador Management Update Component', () => {
  let comp: ContadorUpdateComponent;
  let fixture: ComponentFixture<ContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contadorFormService: ContadorFormService;
  let contadorService: ContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContadorUpdateComponent],
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
      .overrideTemplate(ContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contadorFormService = TestBed.inject(ContadorFormService);
    contadorService = TestBed.inject(ContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const contador: IContador = { id: 456 };

      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      expect(comp.contador).toEqual(contador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContador>>();
      const contador = { id: 123 };
      jest.spyOn(contadorFormService, 'getContador').mockReturnValue(contador);
      jest.spyOn(contadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contador }));
      saveSubject.complete();

      // THEN
      expect(contadorFormService.getContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contadorService.update).toHaveBeenCalledWith(expect.objectContaining(contador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContador>>();
      const contador = { id: 123 };
      jest.spyOn(contadorFormService, 'getContador').mockReturnValue({ id: null });
      jest.spyOn(contadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contador }));
      saveSubject.complete();

      // THEN
      expect(contadorFormService.getContador).toHaveBeenCalled();
      expect(contadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContador>>();
      const contador = { id: 123 };
      jest.spyOn(contadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
