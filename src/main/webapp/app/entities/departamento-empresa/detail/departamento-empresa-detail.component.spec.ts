import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DepartamentoEmpresaDetailComponent } from './departamento-empresa-detail.component';

describe('DepartamentoEmpresa Management Detail Component', () => {
  let comp: DepartamentoEmpresaDetailComponent;
  let fixture: ComponentFixture<DepartamentoEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartamentoEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DepartamentoEmpresaDetailComponent,
              resolve: { departamentoEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DepartamentoEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartamentoEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load departamentoEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DepartamentoEmpresaDetailComponent);

      // THEN
      expect(instance.departamentoEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
