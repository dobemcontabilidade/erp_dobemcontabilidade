import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { TermoAdesaoEmpresaDetailComponent } from './termo-adesao-empresa-detail.component';

describe('TermoAdesaoEmpresa Management Detail Component', () => {
  let comp: TermoAdesaoEmpresaDetailComponent;
  let fixture: ComponentFixture<TermoAdesaoEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermoAdesaoEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TermoAdesaoEmpresaDetailComponent,
              resolve: { termoAdesaoEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TermoAdesaoEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TermoAdesaoEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load termoAdesaoEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TermoAdesaoEmpresaDetailComponent);

      // THEN
      expect(instance.termoAdesaoEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
